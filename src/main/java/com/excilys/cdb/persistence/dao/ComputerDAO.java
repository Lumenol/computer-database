package com.excilys.cdb.persistence.dao;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.exception.ComputerDAOException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.page.OrderBy;
import com.excilys.cdb.persistence.page.Page;
import com.excilys.cdb.persistence.page.Pageable;

@Repository
@Transactional(readOnly = true)
public class ComputerDAO {

    private static final String SQL_COUNT = "SELECT COUNT(id) AS count FROM computer";
    private static final String SQL_COUNT_SEARCH = "SELECT COUNT(A.id) AS count FROM computer AS A LEFT JOIN company AS B ON A.company_id = B.id WHERE UPPER(A.name) LIKE ? OR UPPER(B.name) LIKE ?";

    private static final String SQL_CREATE = "INSERT INTO computer (name, introduced,discontinued,company_id) VALUES (?,?,?,?)";

    private static final String SQL_DELETE = "DELETE FROM computer WHERE id=?";
    private static final String SQL_DELETE_ALL_BY_COMPANY_ID = "DELETE FROM computer WHERE company_id=?";
    private static final String SQL_FIND_ALL_PAGED = "SELECT A.id AS id,A.name AS name ,A.introduced AS introduced ,A.discontinued AS discontinued ,B.id AS company_id,B.name AS company_name FROM computer AS A LEFT JOIN company AS B ON A.company_id = B.id ORDER BY %s LIMIT ? OFFSET ?";
    private static final String SQL_SEARCH = "SELECT A.id AS id,A.name AS name ,A.introduced AS introduced ,A.discontinued AS discontinued ,B.id AS company_id,B.name AS company_name FROM computer AS A LEFT JOIN company AS B ON A.company_id = B.id WHERE UPPER(A.name) LIKE ? OR UPPER(B.name) LIKE ? ORDER BY %s LIMIT ? OFFSET ?";
    private static final String SQL_FIND_BY_ID = "SELECT A.id AS id,A.name AS name ,A.introduced AS introduced ,A.discontinued AS discontinued ,B.id AS company_id,B.name AS company_name FROM computer AS A LEFT JOIN company AS B ON A.company_id = B.id WHERE A.id = ? LIMIT 1";

    private static final String SQL_UPDATE = "UPDATE computer SET name = ?, introduced = ?,discontinued = ?,company_id = ? WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Computer> computerRowMapper;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public ComputerDAO(JdbcTemplate jdbcTemplate, RowMapper<Computer> computeRowMapper) {
	super();
	this.jdbcTemplate = jdbcTemplate;
	this.computerRowMapper = computeRowMapper;
    }

    public long count() {
	try {
	    return jdbcTemplate.queryForObject(SQL_COUNT, Long.class);
	} catch (DataAccessException e) {
	    logger.warn("count()", e);
	    throw new ComputerDAOException(e);
	}
    }

    private PreparedStatementCreator preparedStatementCreatorForCreate(SQLComputer computer) {
	Object[] args = { computer.getName(), computer.getIntroduced(), computer.getDiscontinued(),
		computer.getManufacturerId() };
	return con -> {
	    final PreparedStatement prepareStatement = con.prepareStatement(SQL_CREATE,
		    Statement.RETURN_GENERATED_KEYS);
	    for (int i = 0; i < args.length; i++) {
		prepareStatement.setObject(i + 1, args[i]);
	    }
	    return prepareStatement;
	};
    }

    @Transactional
    public long create(Computer computer) {
	final SQLComputer sqlComputer = SQLComputer.from(computer);
	try {
	    final GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
	    jdbcTemplate.update(preparedStatementCreatorForCreate(sqlComputer), keyHolder);
	    return (long) keyHolder.getKey();
	} catch (DataAccessException e) {
	    logger.warn("create(" + computer + ")", e);
	    throw new ComputerDAOException(e);
	}

    }

    @Transactional
    public void deleteById(long id) {
	try {
	    jdbcTemplate.update(SQL_DELETE, id);
	} catch (DataAccessException e) {
	    logger.warn("deleteById(" + id + ")", e);
	    throw new ComputerDAOException(e);
	}
    }

    @Transactional
    public void deleteByMannufacturerId(long id) {
	try {
	    jdbcTemplate.update(SQL_DELETE_ALL_BY_COMPANY_ID, id);
	} catch (DataAccessException e) {
	    logger.warn("deleteByMannufacturerId(" + id + ")", e);
	    throw new ComputerDAOException(e);
	}
    }

    private String insertOrderByInQuery(String request, OrderBy orderBy) {
	String sql;
	String field;
	String meaning;
	switch (orderBy.getField()) {
	default:
	case ID:
	    field = "A.id";
	    break;
	case NAME:
	    field = "A.name";
	    break;
	case INTRODUCED:
	    field = "A.introduced";
	    break;
	case DISCONTINUED:
	    field = "A.discontinued";
	    break;
	case COMPANY:
	    field = "B.name";
	    break;
	}
	if (orderBy.getMeaning() == OrderBy.Meaning.DESC) {
	    meaning = " DESC";
	} else {
	    meaning = " ASC";
	}

	sql = field + " IS NULL, " + field + " " + meaning + " ,A.name " + meaning;

	return String.format(request, sql);
    }

    public List<Computer> findAll(Pageable pageable) {
	try {
	    final String query = insertOrderByInQuery(SQL_FIND_ALL_PAGED, pageable.getOrderBy());
	    final Page page = pageable.getPage();
	    final Object[] args = { page.getLimit(), page.getOffset() };
	    return jdbcTemplate.query(query, args, computerRowMapper);
	} catch (DataAccessException e) {
	    logger.warn("findAll(" + pageable + ")", e);
	    throw new ComputerDAOException(e);
	}
    }

    public Optional<Computer> findById(long id) {
	try {
	    final Object[] args = { id };
	    return Optional.ofNullable(jdbcTemplate.query(SQL_FIND_BY_ID, args, computerRowMapper))
		    .filter(list -> !list.isEmpty()).map(list -> list.get(0));
	} catch (DataAccessException e) {
	    logger.warn("findById(" + id + ")", e);
	    throw new ComputerDAOException(e);
	}
    }

    @Transactional
    public void update(Computer computer) {
	final SQLComputer sqlComputer = SQLComputer.from(computer);
	try {
	    final Object[] args = { sqlComputer.getName(), sqlComputer.getIntroduced(), sqlComputer.getDiscontinued(),
		    sqlComputer.getManufacturerId(), sqlComputer.getId() };
	    jdbcTemplate.update(SQL_UPDATE, args);
	} catch (DataAccessException e) {
	    logger.warn("update(" + computer + ")", e);
	    throw new ComputerDAOException(e);
	}

    }

    public long countSearch(String search) {
	Objects.requireNonNull(search);
	try {
	    final String like = ("%" + search + "%").toUpperCase();
	    final Object[] args = { like, like };
	    return jdbcTemplate.queryForObject(SQL_COUNT_SEARCH, args, Long.class);
	} catch (DataAccessException e) {
	    logger.warn("count()", e);
	    throw new ComputerDAOException(e);
	}
    }

    public List<Computer> search(Pageable pageable, String search) {
	Objects.requireNonNull(search);
	try {
	    final Page page = pageable.getPage();
	    final String like = ("%" + search + "%").toUpperCase();
	    final String query = insertOrderByInQuery(SQL_SEARCH, pageable.getOrderBy());
	    final Object[] args = { like, like, page.getLimit(), page.getOffset() };
	    return jdbcTemplate.query(query, args, computerRowMapper);
	} catch (DataAccessException e) {
	    logger.warn("search(" + pageable + "," + search + ")", e);
	    throw new ComputerDAOException(e);
	}
    }

}
