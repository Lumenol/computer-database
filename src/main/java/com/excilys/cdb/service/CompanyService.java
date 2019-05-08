package com.excilys.cdb.service;

import com.excilys.cdb.exception.CompanyDAOException;
import com.excilys.cdb.exception.CompanyServiceException;
import com.excilys.cdb.exception.ComputerDAOException;
import com.excilys.cdb.exception.TransactionException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.ConnectionManager;
import com.excilys.cdb.persistence.dao.CompanyDAO;
import com.excilys.cdb.persistence.dao.ComputerDAO;
import com.excilys.cdb.persistence.page.Page;
import com.excilys.cdb.persistence.transaction.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    public void delete(long id) {
	try (final Transaction transaction = ConnectionManager.getInstance().getTransaction()) {
	    ComputerDAO.getInstance().deleteAllByCompanyId(id);
	    companyDAO.deleteById(id);
	    transaction.commit();
	} catch (TransactionException | ComputerDAOException | CompanyDAOException e) {
	    logger.warn("delete(" + id + ")", e);
	    throw new CompanyServiceException(e);
	}
    }
}
