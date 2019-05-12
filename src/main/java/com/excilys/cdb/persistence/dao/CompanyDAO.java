package com.excilys.cdb.persistence.dao;

import com.excilys.cdb.exception.CompanyDAOException;
import com.excilys.cdb.mapper.resultset.ResultSetMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.page.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static com.excilys.cdb.persistence.dao.DAOUtils.haveOneOrEmpty;

@Repository
public class CompanyDAO {

    private static final String SQL_COUNT = "SELECT COUNT(id) AS count FROM company";
    private static final String SQL_FIND_ALL_PAGED = "SELECT id,name FROM company ORDER BY name LIMIT ? OFFSET ?";
    private static final String SQL_FIND_ALL = "SELECT id,name FROM company ORDER BY name";
    private static final String SQL_FIND_BY_ID = "SELECT id,name FROM company WHERE id = ? LIMIT 1";
    private static final String SQL_DELETE = "DELETE FROM company WHERE id=?";
    private static final String SQL_DELETE_ALL_COMPUTER_BY_COMPANY_ID = "DELETE FROM computer WHERE company_id=?";


    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final ResultSetMapper<Long> resultSetToCountMapper;
    private final DataSource dataSource;
    private final ResultSetMapper<List<Company>> resultSetMapper;

    public CompanyDAO(ResultSetMapper<Long> resultSetToCountMapper, DataSource dataSource, ResultSetMapper<List<Company>> resultSetMapper) {
        this.resultSetToCountMapper = resultSetToCountMapper;
        this.dataSource = dataSource;
        this.resultSetMapper = resultSetMapper;
    }

    public long count() {
        try {
            return JDBCUtils.find(resultSetToCountMapper, dataSource, SQL_COUNT);
        } catch (SQLException e) {
            logger.warn("count()", e);
            throw new CompanyDAOException(e);
        }
    }

    public List<Company> findAll(Page page) {
        try {
            return JDBCUtils.find(resultSetMapper, dataSource, SQL_FIND_ALL_PAGED, page.getLimit(), page.getOffset());
        } catch (SQLException e) {
            logger.warn("findAll(" + page + ")", e);
            throw new CompanyDAOException(e);
        }
    }

    public Optional<Company> findById(long id) {
        try {
            List<Company> companies = JDBCUtils.find(resultSetMapper, dataSource, SQL_FIND_BY_ID, id);
            return haveOneOrEmpty(companies);
        } catch (SQLException e) {
            logger.warn("findById(" + id + ")", e);
            throw new CompanyDAOException(e);
        }
    }

    public List<Company> findAll() {
        try {
            return JDBCUtils.find(resultSetMapper, dataSource, SQL_FIND_ALL);
        } catch (SQLException e) {
            logger.warn("findAll()", e);
            throw new CompanyDAOException(e);
        }
    }

    public void deleteById(long id) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            try {
                JDBCUtils.delete(connection, SQL_DELETE_ALL_COMPUTER_BY_COMPANY_ID, id);
                JDBCUtils.delete(connection, SQL_DELETE, id);
                connection.commit();
            } catch (Exception e) {
                connection.rollback();
                throw e;
            }
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            logger.warn("deleteById(" + id + ")", e);
            throw new CompanyDAOException(e);
        }
    }
}
