package com.excilys.cdb.dao;

import com.excilys.cdb.exception.ComputerDAOException;
import com.excilys.cdb.mapper.result.ResultSetMapper;
import com.excilys.cdb.mapper.result.ResultSetToComputerMapper;
import com.excilys.cdb.mapper.result.ResultSetToCountMapper;
import com.excilys.cdb.mapper.result.ResultSetToListMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ComputerDao {

    private static final String SQL_CREATE = "INSERT INTO computer (name, introduced,discontinued,company_id) VALUES (?,?,?,?)";
    private static final String SQL_DELETE = "DELETE FROM computer WHERE id=?";
    private static final String SQL_FIND_ALL_PAGED = "SELECT A.id AS id,A.name AS name ,A.introduced AS introduced ,A.discontinued AS discontinued ,B.id AS company_id,B.name AS company_name FROM computer AS A LEFT JOIN company AS B ON A.company_id = B.id ORDER BY A.id LIMIT ? OFFSET ?";
    private static final String SQL_FIND_BY_ID = "SELECT A.id AS id,A.name AS name ,A.introduced AS introduced ,A.discontinued AS discontinued ,B.id AS company_id,B.name AS company_name FROM computer AS A LEFT JOIN company AS B ON A.company_id = B.id WHERE A.id = ? LIMIT 1";
    private static final String SQL_UPDATE = "UPDATE computer SET name = ?, introduced = ?,discontinued = ?,company_id = ? WHERE id = ?";
    private static final String SQL_COUNT = "SELECT COUNT(id) AS count FROM computer";

    private static ComputerDao instance;
    private final ConnectionProvider connectionProvider = ConnectionProvider.getInstance();
    private final ResultSetMapper<List<Computer>> resultSetMapper = new ResultSetToListMapper<>(ResultSetToComputerMapper.getInstance());

    private ComputerDao() {
    }

    public static ComputerDao getInstance() {
        if (Objects.isNull(instance)) {
            instance = new ComputerDao();
        }
        return instance;
    }

    public long create(Computer computer) {
        String name = computer.getName();

        Date introduced = computer.getIntroduced().map(Date::valueOf).orElse(null);
        Date discontinued = computer.getDiscontinued().map(Date::valueOf).orElse(null);
        Long manufacturerId = computer.getManufacturer().map(Company::getId).orElse(null);

        try {
            return JDBCUtils.insert(connectionProvider, SQL_CREATE, name, introduced, discontinued, manufacturerId);
        } catch (SQLException e) {
            throw new ComputerDAOException(e);
        }

    }

    public void deleteById(long id) {
        try {
            JDBCUtils.delete(connectionProvider, SQL_DELETE, id);
        } catch (SQLException e) {
            throw new ComputerDAOException(e);
        }
    }

    public List<Computer> findAll(long offset, long limit) {
        try {
            return JDBCUtils.find(resultSetMapper, connectionProvider, SQL_FIND_ALL_PAGED, limit,
                    offset);
        } catch (SQLException e) {
            throw new ComputerDAOException(e);
        }
    }

    public Optional<Computer> findById(long id) {
        try {
            List<Computer> computers = JDBCUtils.find(resultSetMapper, connectionProvider, SQL_FIND_BY_ID,
                    id);
            if (computers.isEmpty()) {
                return Optional.empty();
            } else {
                return Optional.of(computers.get(0));
            }
        } catch (SQLException e) {
            throw new ComputerDAOException(e);
        }
    }

    public void update(Computer computer) {

        Long id = computer.getId();
        String name = computer.getName();
        Date introduced = computer.getIntroduced().map(Date::valueOf).orElse(null);
        Date discontinued = computer.getDiscontinued().map(Date::valueOf).orElse(null);
        Long manufacturerId = computer.getManufacturer().map(Company::getId).orElse(null);

        try {
            JDBCUtils.update(connectionProvider, SQL_UPDATE, name, introduced, discontinued, manufacturerId, id);
        } catch (SQLException e) {
            throw new ComputerDAOException(e);
        }

    }

    public long count() {
        try {
            return JDBCUtils.find(ResultSetToCountMapper.getInstance(), connectionProvider, SQL_COUNT);
        } catch (SQLException e) {
            throw new ComputerDAOException(e);
        }
    }
}
