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
import com.metier.dao.ComputerDAO;
import com.metier.entite.Company;
import com.metier.entite.Computer;
import com.metier.entite.Computer.ComputerBuilder;

public class ComputerDaoJDBC implements ComputerDAO {

    private static final String SQL_FIND_BY_ID = "SELECT A.id AS id,A.name AS name ,A.introduced AS introduced ,A.discontinued AS discontinued ,B.id AS company_id,B.name AS company_name FROM computer AS A LEFT JOIN company AS B ON A.company_id = B.id WHERE A.id = ?";
    private static final String SQL_FIND_ALL = "SELECT A.id AS id,A.name AS name ,A.introduced AS introduced ,A.discontinued AS discontinued ,B.id AS company_id,B.name AS company_name FROM computer AS A LEFT JOIN company AS B ON A.company_id = B.id ORDER BY A.id";
    private static final String SQL_CREATE = "INSERT INTO computer (name, introduced,discontinued,company_id) VALUES (?,?,?,?)";
    private static final String SQL_UPDATE = "UPDATE computer SET(name, introduced,discontinued,company_id) VALUES (?,?,?,?) WHERE id=?";
    private static final String SQL_DELETE = "DELETE FROM computer WHERE id=?";

    private final ConnectionFactory connectionFactory;

    public ComputerDaoJDBC(ConnectionFactory connectionFactory) {
	super();
	this.connectionFactory = connectionFactory;
    }

    private static Computer from(ResultSet resultSet) {
	try {
	    long id = resultSet.getLong("id");
	    String name = resultSet.getString("name");
	    LocalDate introduced = Optional.ofNullable(resultSet.getDate("introduced")).map(Date::toLocalDate)
		    .orElse(null);
	    LocalDate discontinued = Optional.ofNullable(resultSet.getDate("discontinued")).map(Date::toLocalDate)
		    .orElse(null);
	    ComputerBuilder computerBuilder = Computer.builder().id(id).name(name).introduced(introduced)
		    .discontinued(discontinued);
	    Long companyId = resultSet.getLong("company_id");
	    if (Objects.nonNull(companyId)) {
		String compagnyName = resultSet.getString("company_name");
		Company mannufacturer = Company.builder().id(companyId).name(compagnyName).build();
		computerBuilder.manufacturer(mannufacturer);
	    }
	    return computerBuilder.build();

	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	return null;
    }

    @Override
    public List<Computer> findAll() {
	List<Computer> computers = new ArrayList<Computer>();
	try (Connection connection = connectionFactory.getConnection()) {
	    try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL)) {
		statement.execute();
		try (ResultSet resultSet = statement.getResultSet()) {
		    while (resultSet.next()) {
			computers.add(from(resultSet));
		    }
		}
	    }
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	return computers;
    }

    @Override
    public Optional<Computer> findById(long id) {
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

    @Override
    public long create(Computer computer) {
	try (Connection connection = connectionFactory.getConnection()) {
	    try (PreparedStatement statement = connection.prepareStatement(SQL_CREATE,
		    Statement.RETURN_GENERATED_KEYS)) {
		statement.setString(1, computer.getName());
		statement.setDate(2,
			Objects.nonNull(computer.getIntroduced()) ? Date.valueOf(computer.getIntroduced()) : null);
		statement.setDate(3, computer.getDiscontinued().map(Date::valueOf).orElse(null));
		statement.setLong(4,
			Objects.nonNull(computer.getManufacturer()) ? computer.getManufacturer().getId() : null);

		statement.executeUpdate();
		try (ResultSet keys = statement.getGeneratedKeys()) {
		    keys.next();
		    return keys.getLong(1);
		}
	    }
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return -1;
    }

    @Override
    public void update(Computer computer) {
	try (Connection connection = connectionFactory.getConnection()) {
	    try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {
		statement.setString(1, computer.getName());
		statement.setDate(2,
			Objects.nonNull(computer.getIntroduced()) ? Date.valueOf(computer.getIntroduced()) : null);
		statement.setDate(3, computer.getDiscontinued().map(Date::valueOf).orElse(null));
		statement.setLong(4,
			Objects.nonNull(computer.getManufacturer()) ? computer.getManufacturer().getId() : null);
		statement.setLong(5, computer.getId());

		statement.executeUpdate();
	    }
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    @Override
    public void deleteById(long id) {
	try (Connection connection = connectionFactory.getConnection()) {
	    try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE)) {
		statement.setLong(1, id);
		statement.executeUpdate();
	    }
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

}
