package com.infra.dao.jdbc;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.business.dao.CompanyDAO;
import com.business.entite.Company;
import com.infra.dao.ConnectionFactory;
import com.infra.dao.mapper.ResultSetMapper;

public class CompanyDaoJDBC implements CompanyDAO {

    private static final String SQL_FIND_ALL = "SELECT id,name FROM company ORDER BY id";
    private static final String SQL_FIND_BY_ID = "SELECT id,name FROM company WHERE id = ? LIMIT 1";

    private final ConnectionFactory connectionFactory;
    private final ResultSetMapper<List<Company>> resultSetToListCompanyMapper;

    public CompanyDaoJDBC(ConnectionFactory connectionFactory,
	    ResultSetMapper<List<Company>> resultSetToListCompanyMapper) {
	super();
	this.connectionFactory = connectionFactory;
	this.resultSetToListCompanyMapper = resultSetToListCompanyMapper;
    }

    @Override
    public List<Company> findAll() {
	try {
	    return JDBCUtils.find(resultSetToListCompanyMapper, connectionFactory, SQL_FIND_ALL);
	} catch (SQLException e) {
	    throw new RuntimeException(e);
	}
    }

    @Override
    public Optional<Company> findById(long id) {
	try {
	    List<Company> companies = JDBCUtils.find(resultSetToListCompanyMapper, connectionFactory, SQL_FIND_BY_ID,
		    id);
	    if (companies.isEmpty()) {
		return Optional.empty();
	    } else {
		return Optional.of(companies.get(0));
	    }
	} catch (SQLException e) {
	    throw new RuntimeException(e);
	}
    }

}
