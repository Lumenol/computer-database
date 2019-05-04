package com.excilys.cdb.dao;

import com.excilys.cdb.exception.TransactionException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

public class Transaction implements AutoCloseable {
    private static final String TRANSACTION_CLOSED = "La transaction est fermée.";
    private Connection connection;
    private Connection proxy;

    public Transaction(Connection connection) throws SQLException {
        this.connection = connection;
        proxy = TransactionConnectionProxy.proxy(connection);
        connection.setAutoCommit(false);
    }

    public boolean isClose() {
        return Objects.isNull(connection);
    }

    public Connection getConnection() {
        if (isClose()) {
            throw new TransactionException(TRANSACTION_CLOSED);
        }
        return proxy;
    }

    public void commit() throws SQLException {
        if (isClose()) {
            throw new TransactionException(TRANSACTION_CLOSED);
        }
        connection.commit();
    }

    public void rollback() throws SQLException {
        if (isClose()) {
            throw new TransactionException(TRANSACTION_CLOSED);
        }
        connection.rollback();
    }

    @Override
    public void close() throws Exception {
        connection.close();
        connection = proxy = null;
    }
}
