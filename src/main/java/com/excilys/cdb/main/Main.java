package com.excilys.cdb.main;

import com.excilys.cdb.dao.ConnectionProvider;
import com.excilys.cdb.ui.Cli;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Main {

    private static Properties properties(InputStream inStream) throws IOException {
        Properties properties = new Properties();
        properties.load(inStream);
        return properties;
    }

    public static void main(String[] args) throws IOException {
        final InputStream dbProperties = Main.class.getClassLoader().getResourceAsStream("database.properties");
        Properties database = properties(dbProperties);
        String url = database.getProperty("url");
        String username = database.getProperty("username");
        String password = database.getProperty("password");

        ConnectionProvider.getInstance().setConnectionParameters(url, username, password);

        final Cli cli = new Cli(System.in, System.out);
        cli.run();
    }

}
