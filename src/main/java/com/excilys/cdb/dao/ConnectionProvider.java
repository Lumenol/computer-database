package com.excilys.cdb.dao;

import com.excilys.cdb.exception.DriverNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class ConnectionProvider {

    private static ConnectionProvider instance;
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private String password;
    private String url;
    private String user;

    private ConnectionProvider(String url, String user, String password, String driver) {
        super();
        this.url = url;
        this.user = user;
        this.password = password;

        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            logger.warn("Driver {} not found.", driver);
            throw new DriverNotFoundException(e);
        }
    }

    private static ResourceBundle bundle() {
        return ResourceBundle.getBundle("database");
    }

    public static ConnectionProvider getInstance() {
        if (Objects.isNull(instance)) {
            TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
            ResourceBundle bundle = bundle();
            final String url = bundle.getString("url");
            final String username = bundle.getString("username");
            final String password = bundle.getString("password");
            final String driver = bundle.getString("driver");
            instance = new ConnectionProvider(url, username, password, driver);
        }
        return instance;
    }

    public Connection get() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
