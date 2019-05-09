package com.excilys.cdb.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
import java.util.TimeZone;

import javax.sql.DataSource;

import com.excilys.cdb.exception.TransactionException;
import com.excilys.cdb.persistence.transaction.Transaction;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class ConnectionManager implements ConnectionProvider {

    private static final String DATASOURCE_PROPERTIES = "/datasource.properties";
    private static final TimeZone TIME_ZONE_UTC = TimeZone.getTimeZone("UTC");
    private static ConnectionManager instance;
    private final DataSource dataSource;
    private ThreadLocal<Transaction> transactionThreadLocal = new ThreadLocal<>();

    private ConnectionManager(DataSource dataSource) {
	this.dataSource = dataSource;
    }

    public static synchronized ConnectionManager getInstance() {
	if (Objects.isNull(instance)) {
	    TimeZone.setDefault(TIME_ZONE_UTC);
	    HikariConfig config = new HikariConfig(DATASOURCE_PROPERTIES);
	    final HikariDataSource dataSource = new HikariDataSource(config);
	    instance = new ConnectionManager(dataSource);
	}
	return instance;
    }

    public Transaction getTransaction() throws TransactionException {
	final Transaction stored = transactionThreadLocal.get();
	if (Objects.isNull(stored) || stored.isClose()) {
	    try {
		final Transaction transaction = new Transaction(getConnection());
		transactionThreadLocal.set(transaction);
		return transaction;
	    } catch (SQLException e) {
		throw new TransactionException("Erreur à la création", e);
	    }
	} else {
	    return stored;
	}
    }

    @Override
    public Connection getConnection() throws SQLException {
	final Transaction transaction = transactionThreadLocal.get();
	if (Objects.nonNull(transaction)) {
	    if (!transaction.isClose()) {
		return transaction.getConnection();
	    } else {
		transactionThreadLocal.remove();
	    }
	}
	return dataSource.getConnection();
    }
}
