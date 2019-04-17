package com.infra.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import com.business.dao.CompanyDAO;
import com.business.entite.Company;
import com.infra.dao.ConnectionFactory;

public class CompanyDaoJDBC implements CompanyDAO {

    private static final String SQL_FIND_ALL = "SELECT id,name FROM company ORDER BY id";
    private static final String SQL_FIND_BY_ID = "SELECT id,name FROM company WHERE id = ? LIMIT 1";

    private final ConnectionFactory connectionFactory;
    private final Function<ResultSet, Company> resultSetToCompany;

    public CompanyDaoJDBC(ConnectionFactory connectionFactory, Function<ResultSet, Company> resultSetToCompany) {
	super();
	this.connectionFactory = connectionFactory;
	this.resultSetToCompany = resultSetToCompany;
    }

    private <T> T execute(Function<ResultSet, T> doSomething, String sql, Object... args) {
	try (Connection connection = connectionFactory.getConnection();
		PreparedStatement statement = connection.prepareStatement(sql)) {
	    for (int i = 0; i < args.length; i++) {
		statement.setObject(i + 1, args[i]);
	    }
	    statement.execute();
	    try (ResultSet resultSet = statement.getResultSet()) {
		return doSomething.apply(resultSet);
	    }
	} catch (SQLException e) {
	    throw new RuntimeException(e);
	}
    }

    @Override
    public List<Company> findAll() {
	return execute(this::mappingToCompany, SQL_FIND_ALL);
    }

    @Override
    public Optional<Company> findById(long id) {
	List<Company> companies = execute(this::mappingToCompany, SQL_FIND_BY_ID, id);
	if (companies.isEmpty()) {
	    return Optional.empty();
	} else {
	    return Optional.of(companies.get(0));
	}
    }

    private List<Company> mappingToCompany(ResultSet rs) {
	List<Company> companies = new ArrayList<>();
	try {
	    while (rs.next()) {
		companies.add(resultSetToCompany.apply(rs));
	    }
	} catch (SQLException e) {
	    throw new RuntimeException(e);
	}
	return companies;
    }

}
