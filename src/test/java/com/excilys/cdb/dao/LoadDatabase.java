package com.excilys.cdb.dao;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.StringTokenizer;

public class LoadDatabase {
    private LoadDatabase() {
    }

    public static void load() throws SQLException, IOException {
	executeScript("schema.sql");
    }

    private static void executeScript(String filename) throws SQLException, IOException {
	try (final Connection connection = ConnectionProvider.getInstance().get();
		final Statement statement = connection.createStatement();
		final InputStream resourceAsStream = LoadDatabase.class.getClassLoader().getResourceAsStream(filename);
		final Scanner scanner = new Scanner(resourceAsStream)) {

	    StringBuilder sb = new StringBuilder();
	    while (scanner.hasNextLine()) {
		final String nextLine = scanner.nextLine();
		sb.append(nextLine);
	    }
	    final StringTokenizer stringTokenizer = new StringTokenizer(sb.toString(), ";");

	    while (stringTokenizer.hasMoreTokens()) {
		final String nextToken = stringTokenizer.nextToken();
		statement.execute(nextToken);
	    }
	}

    }
}
