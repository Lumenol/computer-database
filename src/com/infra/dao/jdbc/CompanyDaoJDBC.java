package com.infra.dao.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.infra.dao.ConnectionFactory;
import com.metier.dao.CompanyDAO;
import com.metier.dao.ComputerDAO;
import com.metier.entite.Company;
import com.metier.entite.Computer;

public class CompanyDaoJDBC implements CompanyDAO {

    private static final String SQL_FIND_ALL = "SELECT id,name FROM company ORDER BY id";
    private static final String SQL_FIND_BY_ID = "SELECT id,name FROM company WHERE id = ?";

    private final ConnectionFactory connectionFactory;

    public CompanyDaoJDBC(ConnectionFactory connectionFactory) {
	super();
	this.connectionFactory = connectionFactory;
    }

    private static Company from(ResultSet resultSet) {
	try {
	    long id = resultSet.getLong("id");
	    String name = resultSet.getString("name");
	   
	    return Company.builder().id(id).name(name).build();
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	return null;
    }

    @Override
    public List<Company> findAll() {
	List<Company> companies = new ArrayList<>();
	try (Connection connection = connectionFactory.getConnection()) {
	    try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL)) {
		statement.execute();
		try (ResultSet resultSet = statement.getResultSet()) {
		    while (resultSet.next()) {
			companies.add(from(resultSet));
		    }
		}
	    }
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	return companies;
    }

    @Override
    public Optional<Company> findById(long id) {
	try (Connection connection = connectionFactory.getConnection()) {
	    try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ID)) {
		statement.setLong(1, id);
		statement.execute();
		try (ResultSet resultSet = statement.getResultSet()) {
		    if (resultSet.next()) {
			return Optional.of(from(resultSet));
		    }
		}
	    }
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	return Optional.empty();
    }

}
