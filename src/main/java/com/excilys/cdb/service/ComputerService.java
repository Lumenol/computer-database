package com.excilys.cdb.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.excilys.cdb.exception.ComputerDAOException;
import com.excilys.cdb.exception.ComputerServiceException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.dao.ComputerDAO;
import com.excilys.cdb.persistence.page.Pageable;

@Service
public class ComputerService {

    private final ComputerDAO computerDAO;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public ComputerService(ComputerDAO computerDAO) {
	super();
	this.computerDAO = computerDAO;
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
