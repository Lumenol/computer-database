package com.excilys.cdb.persistence.dao;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.exception.CompanyDAOException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.page.Page;

@Repository
@Transactional(readOnly = true)
public class CompanyDAO {

    private static final String SQL_COUNT = "SELECT COUNT(id) AS count FROM company";
    private static final String SQL_FIND_ALL_PAGED = "SELECT id,name FROM company ORDER BY name LIMIT ? OFFSET ?";
    private static final String SQL_FIND_ALL = "SELECT id,name FROM company ORDER BY name";
    private static final String SQL_FIND_BY_ID = "SELECT id,name FROM company WHERE id = ? LIMIT 1";
    private static final String SQL_DELETE = "DELETE FROM company WHERE id=?";

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Company> companyRowMapper;

    public CompanyDAO(JdbcTemplate jdbcTemplate, RowMapper<Company> companyRowMapper) {
	super();
	this.jdbcTemplate = jdbcTemplate;
	this.companyRowMapper = companyRowMapper;
    }

    public long count() {
	try {
	    return jdbcTemplate.queryForObject(SQL_COUNT, Long.class);
	} catch (DataAccessException e) {
	    logger.warn("count()", e);
	    throw new CompanyDAOException(e);
	}
    }

    public List<Company> findAll(Page page) {
	try {
	    final Object[] args = { page.getSize(), page.getOffset() };
	    return jdbcTemplate.query(SQL_FIND_ALL_PAGED, args, companyRowMapper);
	} catch (DataAccessException e) {
	    logger.warn("findAll(" + page + ")", e);
	    throw new CompanyDAOException(e);
	}
    }

    public Optional<Company> findById(long id) {
	try {
	    final Object[] args = { id };
	    return Optional.ofNullable(jdbcTemplate.query(SQL_FIND_BY_ID, args, companyRowMapper))
		    .filter(list -> !list.isEmpty()).map(list -> list.get(0));
	} catch (DataAccessException e) {
	    logger.warn("findById(" + id + ")", e);
	    throw new CompanyDAOException(e);
	}
    }

    public List<Company> findAll() {
	try {
	    return jdbcTemplate.query(SQL_FIND_ALL, companyRowMapper);
	} catch (DataAccessException e) {
	    logger.warn("findAll()", e);
	    throw new CompanyDAOException(e);
	}
    }

    @Transactional
    public void deleteById(long id) {
	try {
	    final Object[] args = { id };
	    jdbcTemplate.update(SQL_DELETE, args);
	} catch (DataAccessException e) {
	    logger.warn("deleteById(" + id + ")", e);
	    throw new CompanyDAOException(e);
	}
    }
}
