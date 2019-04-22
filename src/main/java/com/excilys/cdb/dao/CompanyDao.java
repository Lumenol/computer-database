package com.excilys.cdb.dao;

import com.excilys.cdb.exception.CompanyDAOException;
import com.excilys.cdb.mapper.result.ResultSetMapper;
import com.excilys.cdb.mapper.result.ResultSetToCompanyMapper;
import com.excilys.cdb.mapper.result.ResultSetToCountMapper;
import com.excilys.cdb.mapper.result.ResultSetToListMapper;
import com.excilys.cdb.model.Company;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.excilys.cdb.dao.DAOUtils.haveOneOrEmpty;

public class CompanyDao {

    private static final String SQL_FIND_ALL_PAGED = "SELECT id,name FROM company ORDER BY id LIMIT ? OFFSET ?";
    private static final String SQL_FIND_BY_ID = "SELECT id,name FROM company WHERE id = ? LIMIT 1";
    private static final String SQL_COUNT = "SELECT COUNT(id) AS count FROM company";

    private static CompanyDao instance;
    private final ConnectionProvider connectionProvider = ConnectionProvider.getInstance();
    private final ResultSetMapper<List<Company>> resultSetMapper = new ResultSetToListMapper<>(ResultSetToCompanyMapper.getInstance());
    private final ResultSetToCountMapper resultSetToCountMapper = ResultSetToCountMapper.getInstance();

    private CompanyDao() {
    }

    public static CompanyDao getInstance() {
        if (Objects.isNull(instance)) {
            instance = new CompanyDao();
        }
        return instance;
    }

    public List<Company> findAll(long offset, long limit) {
        try {
            return JDBCUtils.find(resultSetMapper, connectionProvider, SQL_FIND_ALL_PAGED, limit, offset);
        } catch (SQLException e) {
            throw new CompanyDAOException(e);
        }
    }

    public Optional<Company> findById(long id) {
        try {
            List<Company> companies = JDBCUtils.find(resultSetMapper, connectionProvider, SQL_FIND_BY_ID, id);
            return haveOneOrEmpty(companies);
        } catch (SQLException e) {
            throw new CompanyDAOException(e);
        }
    }

    public long count() {
        try {
            return JDBCUtils.find(resultSetToCountMapper, connectionProvider, SQL_COUNT);
        } catch (SQLException e) {
            throw new CompanyDAOException(e);
        }
    }
}
