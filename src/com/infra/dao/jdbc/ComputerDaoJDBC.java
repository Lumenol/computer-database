package com.infra.dao.jdbc;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.business.dao.ComputerDAO;
import com.business.entite.Computer;
import com.infra.dao.ConnectionFactory;
import com.infra.dao.mapper.ResultSetMapper;

public class ComputerDaoJDBC implements ComputerDAO {

    private static final String SQL_FIND_BY_ID = "SELECT A.id AS id,A.name AS name ,A.introduced AS introduced ,A.discontinued AS discontinued ,B.id AS company_id,B.name AS company_name FROM computer AS A LEFT JOIN company AS B ON A.company_id = B.id WHERE A.id = ? LIMIT 1";
    private static final String SQL_FIND_ALL = "SELECT A.id AS id,A.name AS name ,A.introduced AS introduced ,A.discontinued AS discontinued ,B.id AS company_id,B.name AS company_name FROM computer AS A LEFT JOIN company AS B ON A.company_id = B.id ORDER BY A.id";
    private static final String SQL_CREATE = "INSERT INTO computer (name, introduced,discontinued,company_id) VALUES (?,?,?,?)";
    private static final String SQL_UPDATE = "UPDATE computer SET name = ?, introduced = ?,discontinued = ?,company_id = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM computer WHERE id=?";

    private final ConnectionFactory connectionFactory;

    private final ResultSetMapper<List<Computer>> resultSetToListComputerMapper;

    @Override
    public long create(Computer computer) {
	String name = computer.getName();

	Date introduced = null;
	if (Objects.nonNull(computer.getIntroduced())) {
	    introduced = Date.valueOf(computer.getIntroduced());
	}

	Date discontinued = null;
	if (computer.getDiscontinued().isPresent()) {
	    discontinued = Date.valueOf(computer.getDiscontinued().get());
	}

	Long manufacturerId = null;
	if (Objects.nonNull(computer.getManufacturer())) {
	    manufacturerId = computer.getManufacturer().getId();
	}

	try {
	    return JDBCUtils.insert(connectionFactory, SQL_CREATE, name, introduced, discontinued, manufacturerId);
	} catch (SQLException e) {
	    throw new RuntimeException(e);
	}

    }

    @Override
    public void deleteById(long id) {
	try {
	    JDBCUtils.delete(connectionFactory, SQL_DELETE, id);
	} catch (SQLException e) {
	    throw new RuntimeException(e);
	}
    }

    @Override
    public List<Computer> findAll() {
	try {
	    return JDBCUtils.find(resultSetToListComputerMapper, connectionFactory, SQL_FIND_ALL);
	} catch (SQLException e) {
	    throw new RuntimeException(e);
	}
    }

    public ComputerDaoJDBC(ConnectionFactory connectionFactory,
	    ResultSetMapper<List<Computer>> resultSetToListComputerMapper) {
	super();
	this.connectionFactory = connectionFactory;
	this.resultSetToListComputerMapper = resultSetToListComputerMapper;
    }

    @Override
    public Optional<Computer> findById(long id) {
	try {
	    List<Computer> computers = JDBCUtils.find(resultSetToListComputerMapper, connectionFactory, SQL_FIND_BY_ID,
		    id);
	    if (computers.isEmpty()) {
		return Optional.empty();
	    } else {
		return Optional.of(computers.get(0));
	    }
	} catch (SQLException e) {
	    throw new RuntimeException(e);
	}
    }

    @Override
    public void update(Computer computer) {

	String name = computer.getName();

	Date introduced = null;
	if (Objects.nonNull(computer.getIntroduced())) {
	    introduced = Date.valueOf(computer.getIntroduced());
	}

	Date discontinued = null;
	if (computer.getDiscontinued().isPresent()) {
	    discontinued = Date.valueOf(computer.getDiscontinued().get());
	}

	Long manufacturerId = null;
	if (Objects.nonNull(computer.getManufacturer())) {
	    manufacturerId = computer.getManufacturer().getId();
	}
	Long id = computer.getId();

	try {
	    JDBCUtils.update(connectionFactory, SQL_UPDATE, name, introduced, discontinued, manufacturerId, id);
	} catch (SQLException e) {
	    throw new RuntimeException(e);
	}

    }

}
