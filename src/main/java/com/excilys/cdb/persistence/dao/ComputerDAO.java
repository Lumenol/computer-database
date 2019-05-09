package com.excilys.cdb.persistence.dao;

import com.excilys.cdb.exception.ComputerDAOException;
import com.excilys.cdb.mapper.resultset.ResultSetMapper;
import com.excilys.cdb.mapper.resultset.ResultSetToComputerMapper;
import com.excilys.cdb.mapper.resultset.ResultSetToCountMapper;
import com.excilys.cdb.mapper.resultset.ResultSetToListMapper;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.ConnectionManager;
import com.excilys.cdb.persistence.ConnectionProvider;
import com.excilys.cdb.persistence.page.OrderBy;
import com.excilys.cdb.persistence.page.Pageable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ComputerDAO {

    private static final String SQL_COUNT = "SELECT COUNT(id) AS count FROM computer";
    private static final String SQL_COUNT_SEARCH = "SELECT COUNT(A.id) AS count FROM computer AS A LEFT JOIN company AS B ON A.company_id = B.id WHERE UPPER(A.name) LIKE ? OR UPPER(B.name) LIKE ?";

    private static final String SQL_CREATE = "INSERT INTO computer (name, introduced,discontinued,company_id) VALUES (?,?,?,?)";

    private static final String SQL_DELETE_ALL_BY_COMPANY_ID = "DELETE FROM computer WHERE company_id=?";
    private static final String SQL_DELETE = "DELETE FROM computer WHERE id=?";

    private static final String SQL_FIND_ALL_PAGED = "SELECT A.id AS id,A.name AS name ,A.introduced AS introduced ,A.discontinued AS discontinued ,B.id AS company_id,B.name AS company_name FROM computer AS A LEFT JOIN company AS B ON A.company_id = B.id ORDER BY %s LIMIT ? OFFSET ?";
    private static final String SQL_SEARCH = "SELECT A.id AS id,A.name AS name ,A.introduced AS introduced ,A.discontinued AS discontinued ,B.id AS company_id,B.name AS company_name FROM computer AS A LEFT JOIN company AS B ON A.company_id = B.id WHERE UPPER(A.name) LIKE ? OR UPPER(B.name) LIKE ? ORDER BY %s LIMIT ? OFFSET ?";
    private static final String SQL_FIND_BY_ID = "SELECT A.id AS id,A.name AS name ,A.introduced AS introduced ,A.discontinued AS discontinued ,B.id AS company_id,B.name AS company_name FROM computer AS A LEFT JOIN company AS B ON A.company_id = B.id WHERE A.id = ? LIMIT 1";

    private static final String SQL_UPDATE = "UPDATE computer SET name = ?, introduced = ?,discontinued = ?,company_id = ? WHERE id = ?";
    private static ComputerDAO instance;
    private final ConnectionProvider connectionManager = ConnectionManager.getInstance();
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final ResultSetMapper<List<Computer>> resultSetMapper = new ResultSetToListMapper<>(
            ResultSetToComputerMapper.getInstance());
    private final ResultSetToCountMapper resultSetToCountMapper = ResultSetToCountMapper.getInstance();

    private ComputerDAO() {
    }

    public static synchronized ComputerDAO getInstance() {
        if (Objects.isNull(instance)) {
            instance = new ComputerDAO();
        }
        return instance;
    }

    public long count() {
        try {
            return JDBCUtils.find(resultSetToCountMapper, connectionManager, SQL_COUNT);
        } catch (SQLException e) {
            logger.warn("count()", e);
            throw new ComputerDAOException(e);
        }
    }

    public long create(Computer computer) {
        final SQLComputer sqlComputer = SQLComputer.from(computer);
        try {
            return JDBCUtils.insert(connectionManager, SQL_CREATE, sqlComputer.getName(), sqlComputer.getIntroduced(),
                    sqlComputer.getDiscontinued(), sqlComputer.getManufacturerId());
        } catch (SQLException e) {
            logger.warn("create(" + computer + ")", e);
            throw new ComputerDAOException(e);
        }

    }

    public void deleteById(long id) {
        try {
            JDBCUtils.delete(connectionManager, SQL_DELETE, id);
        } catch (SQLException e) {
            logger.warn("deleteById(" + id + ")", e);
            throw new ComputerDAOException(e);
        }
    }

    private String insertOrderByInQuery(String request, OrderBy orderBy) {
        String sql;
        String meaning;
        switch (orderBy.getField()) {
            default:
            case ID:
                sql = "id";
                break;
            case NAME:
                sql = "name";
                break;
            case INTRODUCED:
                sql = "introduced";
                break;
            case DISCONTINUED:
                sql = "discontinued";
                break;
            case COMPANY:
                sql = "company_name";
                break;
        }
        if (orderBy.getMeaning() == OrderBy.Meaning.DESC) {
            meaning = " DESC";
        } else {
            meaning = " ASC";
        }

        sql += meaning + ",name" + meaning;

        return String.format(request, sql);
    }

    public List<Computer> findAll(Pageable pageable) {
        try {
            final long offset = pageable.getPage().getOffset();
            final long limit = pageable.getPage().getLimit();
            final String query = insertOrderByInQuery(SQL_FIND_ALL_PAGED, pageable.getOrderBy());
            return JDBCUtils.find(resultSetMapper, connectionManager, query, limit, offset);
        } catch (SQLException e) {
            logger.warn("findAll(" + pageable + ")", e);
            throw new ComputerDAOException(e);
        }
    }

    public Optional<Computer> findById(long id) {
        try {
            List<Computer> computers = JDBCUtils.find(resultSetMapper, connectionManager, SQL_FIND_BY_ID, id);
            return DAOUtils.haveOneOrEmpty(computers);
        } catch (SQLException e) {
            logger.warn("findById(" + id + ")", e);
            throw new ComputerDAOException(e);
        }
    }

    public void update(Computer computer) {
        final SQLComputer sqlComputer = SQLComputer.from(computer);
        try {
            JDBCUtils.update(connectionManager, SQL_UPDATE, sqlComputer.getName(), sqlComputer.getIntroduced(),
                    sqlComputer.getDiscontinued(), sqlComputer.getManufacturerId(), sqlComputer.getId());
        } catch (SQLException e) {
            logger.warn("update(" + computer + ")", e);
            throw new ComputerDAOException(e);
        }

    }

    public void deleteAllByCompanyId(long id) {
        try {
            JDBCUtils.delete(connectionManager, SQL_DELETE_ALL_BY_COMPANY_ID, id);
        } catch (SQLException e) {
            logger.warn("deleteAllByCompanyId(" + id + ")", e);
            throw new ComputerDAOException(e);
        }
    }

    public long countSearch(String search) {
        Objects.requireNonNull(search);
        try {
            final String like = ("%" + search + "%").toUpperCase();
            return JDBCUtils.find(resultSetToCountMapper, connectionManager, SQL_COUNT_SEARCH, like, like);
        } catch (SQLException e) {
            logger.warn("count()", e);
            throw new ComputerDAOException(e);
        }
    }

    public List<Computer> search(Pageable pageable, String search) {
        Objects.requireNonNull(search);
        try {
            final long offset = pageable.getPage().getOffset();
            final long limit = pageable.getPage().getLimit();
            final String like = ("%" + search + "%").toUpperCase();
            final String query = insertOrderByInQuery(SQL_SEARCH, pageable.getOrderBy());
            return JDBCUtils.find(resultSetMapper, connectionManager, query, like, like, limit, offset);
        } catch (SQLException e) {
            logger.warn("search(" + pageable + "," + search + ")", e);
            throw new ComputerDAOException(e);
        }
    }
}
