package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class ConnectionProvider {

    private static ConnectionProvider instance;
    private String url;
    private String user;
    private String password;

    public static ConnectionProvider getInstance() {
	if (Objects.isNull(instance)) {
	    ResourceBundle bundle = bundle();

	    final String url = bundle.getString("url");
	    final String username = bundle.getString("username");
	    final String password = bundle.getString("password");

	    instance = new ConnectionProvider(url, username, password);
	}
	return instance;
    }

    private static ResourceBundle bundle() {
	return ResourceBundle.getBundle("database");
    }

    private ConnectionProvider(String url, String user, String password) {
	super();
	this.url = url;
	this.user = user;
	this.password = password;
    }

    public Connection get() throws SQLException {
	return DriverManager.getConnection(url, user, password);
    }
}
