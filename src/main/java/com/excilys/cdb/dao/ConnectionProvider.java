package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

public class ConnectionProvider {

    private static ConnectionProvider instance;
    private String url;
    private String user;
    private String password;

    public static ConnectionProvider getInstance() {
        if (Objects.isNull(instance)) {
            instance = new ConnectionProvider();
        }
        return instance;
    }

    public void setConnectionParameters(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public Connection get() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
