package com.excilys.cdb.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
import java.util.TimeZone;

public class ConnectionProvider {

    private static final String DATASOURCE_PROPERTIES = "/datasource.properties";
    private static final TimeZone TIME_ZONE_UTC = TimeZone.getTimeZone("UTC");
    private static ConnectionProvider instance;
    private final DataSource dataSource;

    private ConnectionProvider(DataSource dataSource) {
	this.dataSource = dataSource;
    }

    public static synchronized ConnectionProvider getInstance() {
	if (Objects.isNull(instance)) {
	    TimeZone.setDefault(TIME_ZONE_UTC);
	    HikariConfig config = new HikariConfig(DATASOURCE_PROPERTIES);
	    final HikariDataSource dataSource = new HikariDataSource(config);
	    instance = new ConnectionProvider(dataSource);
	}
	return instance;
    }

    public Connection get() throws SQLException {
	return dataSource.getConnection();
    }
}
