package com.infra.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.infra.dao.ConnectionFactory;
import com.infra.dao.mapper.ResultSetMapper;

public class JDBCUtils {
    private static <T> T execute(ResultSetMapper<T> mapper, ConnectionFactory connectionFactory,
	    StatementFactory statementFactory, StatementExecutor executor, ResultProvider resultProvider, String sql,
	    Object[] args) throws SQLException {
	try (Connection connection = connectionFactory.getConnection();
		PreparedStatement statement = statementFactory.create(connection, sql)) {
	    for (int i = 0; i < args.length; i++) {
		statement.setObject(i + 1, args[i]);
	    }
	    executor.execute(statement);
	    try (ResultSet resultSet = resultProvider.result(statement)) {
		return mapper.map(resultSet);
	    }
	}
    }

    private interface StatementFactory {
	PreparedStatement create(Connection co, String sql) throws SQLException;
    }

    private interface StatementExecutor {
	void execute(PreparedStatement ps) throws SQLException;
    }

    private interface ResultProvider {
	ResultSet result(PreparedStatement ps) throws SQLException;
    }

    private static StatementFactory simpleStatementFactory = (Connection co, String sql) -> co.prepareStatement(sql);
    private static StatementFactory generetedKeysStatementFactory = (Connection co, String sql) -> co
	    .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

    private static StatementExecutor execute = PreparedStatement::execute;
    private static StatementExecutor executeUpdate = PreparedStatement::executeUpdate;

    private static ResultProvider noResult = ps -> null;
    private static ResultProvider resultSet = PreparedStatement::getResultSet;
    private static ResultProvider generatedKeys = PreparedStatement::getGeneratedKeys;

    public static void delete(ConnectionFactory connectionFactory, String sql, Object... args) throws SQLException {
	execute(ResultSetMapper.identity(), connectionFactory, simpleStatementFactory, executeUpdate, noResult, sql,
		args);
    }

    public static void update(ConnectionFactory connectionFactory, String sql, Object... args) throws SQLException {
	execute(ResultSetMapper.identity(), connectionFactory, simpleStatementFactory, executeUpdate, noResult, sql,
		args);
    }

    public static <T> T find(ResultSetMapper<T> mapper, ConnectionFactory connectionFactory, String sql, Object... args)
	    throws SQLException {
	return execute(mapper, connectionFactory, simpleStatementFactory, execute, resultSet, sql, args);
    }

    public static <T> T insert(ResultSetMapper<T> mapper, ConnectionFactory connectionFactory, String sql,
	    Object... args) throws SQLException {
	return execute(mapper, connectionFactory, generetedKeysStatementFactory, executeUpdate, generatedKeys, sql,
		args);
    }
}