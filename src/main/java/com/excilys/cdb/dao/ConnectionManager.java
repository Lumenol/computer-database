package com.excilys.cdb.dao;

import com.excilys.cdb.exception.TransactionException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
import java.util.TimeZone;

public class ConnectionManager {

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
        if (Objects.isNull(stored)) {
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
