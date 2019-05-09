package com.excilys.cdb.service;

import com.excilys.cdb.exception.ComputerDAOException;
import com.excilys.cdb.exception.ComputerServiceException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.dao.ComputerDAO;
import com.excilys.cdb.persistence.page.Pageable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ComputerService {

    private static ComputerService instance;
    private final ComputerDAO computerDAO = ComputerDAO.getInstance();
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private ComputerService() {
    }

    public static synchronized ComputerService getInstance() {
        if (Objects.isNull(instance)) {
            instance = new ComputerService();
        }
        return instance;
    }

    public long count() {
        try {
            return computerDAO.count();
        } catch (ComputerDAOException e) {
            logger.warn("count()", e);
            throw new ComputerServiceException(e);
        }
    }

    public void create(Computer computer) {
        try {
            computerDAO.create(computer);
        } catch (ComputerDAOException e) {
            logger.warn("create(" + computer + ")", e);
            throw new ComputerServiceException(e);
        }
    }

    public void delete(long id) {
        try {
            computerDAO.deleteById(id);
        } catch (ComputerDAOException e) {
            logger.warn("delete(" + id + ")", e);
            throw new ComputerServiceException(e);
        }
    }

    public boolean exist(long id) {
        try {
            return findById(id).isPresent();
        } catch (ComputerDAOException e) {
            logger.warn("exist(" + id + ")", e);
            throw new ComputerServiceException(e);
        }
    }

    public List<Computer> findAll(Pageable pageable) {
        try {
            return computerDAO.findAll(pageable);
        } catch (ComputerDAOException e) {
            logger.warn("findAll(" + pageable + ")", e);
            throw new ComputerServiceException(e);
        }
    }

    public Optional<Computer> findById(long id) {
        try {
            return computerDAO.findById(id);
        } catch (ComputerDAOException e) {
            logger.warn("findById(" + id + ")", e);
            throw new ComputerServiceException(e);
        }
    }

    public void update(Computer computer) {
        try {
            computerDAO.update(computer);
        } catch (ComputerDAOException e) {
            logger.warn("update(" + computer + ")", e);
            throw new ComputerServiceException(e);
        }
    }

    public List<Computer> search(Pageable pageable, String search) {
        try {
            return computerDAO.search(pageable, search);
        } catch (ComputerDAOException e) {
            logger.warn("search(" + pageable + "," + search + ")", e);
            throw new ComputerServiceException(e);
        }
    }

    public long countSearch(String search) {
        try {
            return computerDAO.countSearch(search);
        } catch (ComputerDAOException e) {
            logger.warn("countSearch(" + search + ")", e);
            throw new ComputerServiceException(e);
        }
    }
}
