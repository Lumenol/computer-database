package com.excilys.cdb.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.exception.CompanyDAOException;
import com.excilys.cdb.exception.CompanyServiceException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.dao.CompanyDAO;

public class CompanyService {
    private static CompanyService instance;
    private final CompanyDAO companyDAO = CompanyDAO.getInstance();
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private CompanyService() {
    }

    public static synchronized CompanyService getInstance() {
	if (Objects.isNull(instance)) {
	    instance = new CompanyService();
	}
	return instance;
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

    public List<Company> findAll(long offset, long limit) {
	try {
	    return companyDAO.findAll(offset, limit);
	} catch (CompanyDAOException e) {
	    logger.warn("findAll(" + offset + "," + limit + ")", e);
	    throw new CompanyServiceException(e);
	}
    }

    public Optional<Company> findById(long id) {
	try {
	    return companyDAO.findById(id);
	} catch (CompanyDAOException e) {
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

    public long create(Company company) {
	try {
	    return companyDAO.create(company);
	} catch (CompanyDAOException e) {
	    logger.warn("create(" + company + ")", e);
	    throw new CompanyServiceException(e);
	}
    }
}
