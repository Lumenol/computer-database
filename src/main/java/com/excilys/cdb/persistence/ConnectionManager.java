package com.excilys.cdb.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

import javax.sql.DataSource;

import org.springframework.stereotype.Component;

import com.excilys.cdb.exception.TransactionException;
import com.excilys.cdb.persistence.transaction.Transaction;

@Component
public class ConnectionManager implements ConnectionProvider {

    private final DataSource dataSource;
    private final ThreadLocal<Transaction> transactionThreadLocal = new ThreadLocal<>();

    private ConnectionManager(DataSource dataSource) {
	this.dataSource = dataSource;
    }

    public Transaction getTransaction() {
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
