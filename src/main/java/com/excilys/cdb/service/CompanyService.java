package com.excilys.cdb.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.exception.CompanyDAOException;
import com.excilys.cdb.exception.CompanyServiceException;
import com.excilys.cdb.exception.ComputerDAOException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.dao.CompanyDAO;
import com.excilys.cdb.persistence.dao.ComputerDAO;
import com.excilys.cdb.persistence.page.Page;

@Service
@Transactional(readOnly = true)
public class CompanyService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final CompanyDAO companyDAO;
    private final ComputerDAO computerDAO;

    public CompanyService(CompanyDAO companyDAO, ComputerDAO computerDAO) {
	this.companyDAO = companyDAO;
	this.computerDAO = computerDAO;
    }

    public long count() {
	try {
	    return companyDAO.count();
	} catch (CompanyDAOException e) {
	    logger.warn("count()", e);
	    throw new CompanyServiceException(e);
	}
    }

    public boolean exist(long id) {
	try {
	    return findById(id).isPresent();
	} catch (CompanyDAOException e) {
	    logger.warn("exist(" + id + ")", e);
	    throw new CompanyServiceException(e);
	}
    }

    public List<Company> findAll(Page page) {
	try {
	    return companyDAO.findAll(page);
	} catch (CompanyDAOException e) {
	    logger.warn("findAll(" + page + ")", e);
	    throw new CompanyServiceException(e);
	}
    }

    public Optional<Company> findById(long id) {
	try {
	    return companyDAO.findById(id);
	} catch (CompanyServiceException e) {
	    logger.warn("findById(" + id + ")", e);
	    throw new CompanyServiceException(e);
	}
    }

    public List<Company> findAll() {
	try {
	    return companyDAO.findAll();
	} catch (CompanyDAOException e) {
	    logger.warn("findAll()", e);
	    throw new CompanyServiceException(e);
	}
    }

    @Transactional
    public void delete(long id) {
	try {
	    computerDAO.deleteByMannufacturerId(id);
	    companyDAO.deleteById(id);
	} catch (ComputerDAOException | CompanyDAOException e) {
	    logger.warn("delete(" + id + ")", e);
	    throw new CompanyServiceException(e);
	}
    }
}
