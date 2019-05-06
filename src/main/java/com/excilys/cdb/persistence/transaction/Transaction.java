package com.excilys.cdb.persistence.transaction;

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

    public void commit() {
        if (isClose()) {
            throw new TransactionException(TRANSACTION_CLOSED);
        }
        try {
            connection.commit();
        } catch (SQLException e) {
            throw new TransactionException("Echec du commit", e);
        }
    }

    public void rollback() {
        if (isClose()) {
            throw new TransactionException(TRANSACTION_CLOSED);
        }
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new TransactionException("Echec du rollback", e);
        }
    }

    @Override
    public void close() {
        if (!isClose()) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new TransactionException("Echec de la fermeture", e);
            } finally {
                connection = proxy = null;
            }
        }
    }
}
