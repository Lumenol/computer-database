package com.excilys.cdb.persistence.dao;

import com.excilys.cdb.mapper.resultset.ResultSetMapper;

import javax.sql.DataSource;
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

    public static void delete(DataSource dataSource, String sql, Object... args) throws SQLException {
        execute(ResultSetMapper.identity(), dataSource, simpleStatementFactory, executeUpdate, noResult, sql,
                args);
    }

    public static void delete(Connection connection, String sql, Object... args) throws SQLException {
        execute(ResultSetMapper.identity(), connection, simpleStatementFactory, executeUpdate, noResult, sql,
                args);
    }

    private static <T> T execute(ResultSetMapper<T> mapper, DataSource dataSource,
                                 StatementFactory statementFactory, StatementExecutor executor, ResultProvider resultProvider, String sql,
                                 Object[] args) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            return execute(mapper, connection, statementFactory, executor, resultProvider, sql, args);
        }
    }

    private static <T> T execute(ResultSetMapper<T> mapper, Connection connection,
                                 StatementFactory statementFactory, StatementExecutor executor, ResultProvider resultProvider, String sql,
                                 Object[] args) throws SQLException {
        try (PreparedStatement statement = statementFactory.create(connection, sql)) {
            for (int i = 0; i < args.length; i++) {
                statement.setObject(i + 1, args[i]);
            }
            executor.execute(statement);
            try (ResultSet resultSet = resultProvider.result(statement)) {
                return mapper.map(resultSet);
            }
        }
    }

    public static <T> T find(ResultSetMapper<T> mapper, DataSource dataSource, String sql,
                             Object... args) throws SQLException {
        return execute(mapper, dataSource, simpleStatementFactory, execute, resultSet, sql, args);
    }

    public static Long insert(DataSource dataSource, String sql, Object... args) throws SQLException {
        return execute(keyMapper, dataSource, generetedKeysStatementFactory, executeUpdate, generatedKeys, sql,
                args);
    }

    public static void update(DataSource dataSource, String sql, Object... args) throws SQLException {
        execute(ResultSetMapper.identity(), dataSource, simpleStatementFactory, executeUpdate, noResult, sql,
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