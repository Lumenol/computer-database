package com.excilys.cdb.dao;

import static com.excilys.cdb.dao.DAOUtils.haveOneOrEmpty;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.exception.CompanyDAOException;
import com.excilys.cdb.mapper.resultset.ResultSetMapper;
import com.excilys.cdb.mapper.resultset.ResultSetToCompanyMapper;
import com.excilys.cdb.mapper.resultset.ResultSetToCountMapper;
import com.excilys.cdb.mapper.resultset.ResultSetToListMapper;
import com.excilys.cdb.model.Company;

public class CompanyDAO {

    private static CompanyDAO instance;
    private static final String SQL_COUNT = "SELECT COUNT(id) AS count FROM company";
    private static final String SQL_FIND_ALL_PAGED = "SELECT id,name FROM company ORDER BY id LIMIT ? OFFSET ?";

    private static final String SQL_FIND_BY_ID = "SELECT id,name FROM company WHERE id = ? LIMIT 1";
    public static CompanyDAO getInstance() {
	if (Objects.isNull(instance)) {
	    instance = new CompanyDAO();
	}
	return instance;
    }
    private final ConnectionProvider connectionProvider = ConnectionProvider.getInstance();
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final ResultSetMapper<List<Company>> resultSetMapper = new ResultSetToListMapper<>(
	    ResultSetToCompanyMapper.getInstance());

    private final ResultSetToCountMapper resultSetToCountMapper = ResultSetToCountMapper.getInstance();

    private CompanyDAO() {
    }

    public long count() {
	try {
	    return JDBCUtils.find(resultSetToCountMapper, connectionProvider, SQL_COUNT);
	} catch (SQLException e) {
	    logger.warn("count()", e);
	    throw new CompanyDAOException(e);
	}
    }

    public List<Company> findAll(long offset, long limit) {
	try {
	    return JDBCUtils.find(resultSetMapper, connectionProvider, SQL_FIND_ALL_PAGED, limit, offset);
	} catch (SQLException e) {
	    logger.warn("findAll(" + offset + "," + limit + ")", e);
	    throw new CompanyDAOException(e);
	}
    }

    public Optional<Company> findById(long id) {
	try {
	    List<Company> companies = JDBCUtils.find(resultSetMapper, connectionProvider, SQL_FIND_BY_ID, id);
	    return haveOneOrEmpty(companies);
	} catch (SQLException e) {
	    logger.warn("findById(" + id + ")", e);
	    throw new CompanyDAOException(e);
	}
    }
}
