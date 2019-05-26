package com.excilys.cdb.persistence.dao;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.shared.exception.CompanyDAOException;
import com.excilys.cdb.shared.mapper.FindCompanyById;
import com.excilys.cdb.shared.pagination.Page;
import com.excilys.cdb.shared.validator.CompanyExistById;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public class CompanyDAOImpl implements CompanyExistById, FindCompanyById, CompanyDAO {

    private static final String SQL_COUNT = "SELECT COUNT(id) AS count FROM company";
    private static final String SQL_FIND_ALL_PAGED = "SELECT id,name FROM company ORDER BY name LIMIT ? OFFSET ?";
    private static final String SQL_FIND_ALL = "SELECT id,name FROM company ORDER BY name";
    private static final String SQL_FIND_BY_ID = "SELECT id,name FROM company WHERE id = ? LIMIT 1";
    private static final String SQL_DELETE = "DELETE FROM company WHERE id=?";

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyDAO.class);
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Company> companyRowMapper;

    public CompanyDAOImpl(JdbcTemplate jdbcTemplate, RowMapper<Company> companyRowMapper) {
        super();
        this.jdbcTemplate = jdbcTemplate;
        this.companyRowMapper = companyRowMapper;
    }

    @Override
    public long count() {
        try {
            return jdbcTemplate.queryForObject(SQL_COUNT, Long.class);
        } catch (DataAccessException e) {
            LOGGER.warn("count()", e);
            throw new CompanyDAOException(e);
        }
    }

    @Override
    public List<Company> findAll(Page page) {
        try {
            final Object[] args = {page.getSize(), page.getOffset()};
            return jdbcTemplate.query(SQL_FIND_ALL_PAGED, args, companyRowMapper);
        } catch (DataAccessException e) {
            LOGGER.warn("findAll(" + page + ")", e);
            throw new CompanyDAOException(e);
        }
    }

    @Override
    public Optional<Company> findById(long id) {
        try {
            final Object[] args = {id};
            return Optional.ofNullable(jdbcTemplate.query(SQL_FIND_BY_ID, args, companyRowMapper))
                    .filter(list -> !list.isEmpty()).map(list -> list.get(0));
        } catch (DataAccessException e) {
            LOGGER.warn("findById(" + id + ")", e);
            throw new CompanyDAOException(e);
        }
    }

    @Override
    public List<Company> findAll() {
        try {
            return jdbcTemplate.query(SQL_FIND_ALL, companyRowMapper);
        } catch (DataAccessException e) {
            LOGGER.warn("findAll()", e);
            throw new CompanyDAOException(e);
        }
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        try {
            final Object[] args = {id};
            jdbcTemplate.update(SQL_DELETE, args);
        } catch (DataAccessException e) {
            LOGGER.warn("deleteById(" + id + ")", e);
            throw new CompanyDAOException(e);
        }
    }

    @Override
    public boolean exist(long id) {
        try {
            return findById(id).isPresent();
        } catch (CompanyDAOException e) {
            LOGGER.warn("exist(" + id + ")", e);
            throw new CompanyDAOException(e);
        }
    }

}
