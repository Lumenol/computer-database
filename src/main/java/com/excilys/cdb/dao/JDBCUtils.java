package com.excilys.cdb.dao;

import com.excilys.cdb.mapper.resultset.ResultSetMapper;

import java.sql.*;

final class JDBCUtils {

    private static final StatementExecutor execute = PreparedStatement::execute;
    private static final StatementExecutor executeUpdate = PreparedStatement::executeUpdate;
    private static final ResultProvider generatedKeys = PreparedStatement::getGeneratedKeys;
    private static final StatementFactory generetedKeysStatementFactory = (Connection co, String sql) -> co
            .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    private static final ResultSetMapper<Long> keyMapper = ps -> {
        ps.first();
        return ps.getLong(1);
    };
    private static final ResultProvider noResult = ps -> null;
    private static final ResultProvider resultSet = PreparedStatement::getResultSet;
    private static final StatementFactory simpleStatementFactory = Connection::prepareStatement;

    private JDBCUtils() {
    }

    public static void delete(ConnectionManager connectionManager, String sql, Object... args) throws SQLException {
        execute(ResultSetMapper.identity(), connectionManager, simpleStatementFactory, executeUpdate, noResult, sql,
                args);
    }

    private static <T> T execute(ResultSetMapper<T> mapper, ConnectionManager connectionManager,
                                 StatementFactory statementFactory, StatementExecutor executor, ResultProvider resultProvider, String sql,
                                 Object[] args) throws SQLException {
        try (Connection connection = connectionManager.getConnection();
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

    public static <T> T find(ResultSetMapper<T> mapper, ConnectionManager connectionManager, String sql,
                             Object... args) throws SQLException {
        return execute(mapper, connectionManager, simpleStatementFactory, execute, resultSet, sql, args);
    }

    public static Long insert(ConnectionManager connectionManager, String sql, Object... args) throws SQLException {
        return execute(keyMapper, connectionManager, generetedKeysStatementFactory, executeUpdate, generatedKeys, sql,
                args);
    }

    public static void update(ConnectionManager connectionManager, String sql, Object... args) throws SQLException {
        execute(ResultSetMapper.identity(), connectionManager, simpleStatementFactory, executeUpdate, noResult, sql,
                args);
    }

    private interface ResultProvider {
        ResultSet result(PreparedStatement ps) throws SQLException;
    }

    private interface StatementExecutor {
        void execute(PreparedStatement ps) throws SQLException;
    }

    private interface StatementFactory {
        PreparedStatement create(Connection co, String sql) throws SQLException;
    }
}