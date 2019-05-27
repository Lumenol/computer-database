package com.excilys.cdb.service;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.dao.CompanyDAO;
import com.excilys.cdb.persistence.dao.ComputerDAO;
import com.excilys.cdb.persistence.exception.CompanyDAOException;
import com.excilys.cdb.persistence.exception.ComputerDAOException;
import com.excilys.cdb.service.exception.CompanyServiceException;
import com.excilys.cdb.shared.mapper.FindCompanyById;
import com.excilys.cdb.shared.pagination.Page;
import com.excilys.cdb.shared.validator.CompanyExistById;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class CompanyServiceImpl implements CompanyService, CompanyExistById, FindCompanyById {
    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyService.class);
    private final CompanyDAO companyDAO;
    private final ComputerDAO computerDAO;

    public CompanyServiceImpl(CompanyDAO companyDAO, ComputerDAO computerDAO) {
        this.companyDAO = companyDAO;
        this.computerDAO = computerDAO;
    }

    @Override
    public long count() {
        try {
            return companyDAO.count();
        } catch (CompanyDAOException e) {
            LOGGER.error("count()", e);
            throw new CompanyServiceException(e);
        }
    }

    @Override
    public boolean exist(long id) {
        try {
            return companyDAO.exist(id);
        } catch (CompanyDAOException e) {
            LOGGER.error("exist(" + id + ")", e);
            throw new CompanyServiceException(e);
        }
    }

    @Override
    public List<Company> findAll(Page page) {
        try {
            return companyDAO.findAll(page);
        } catch (CompanyDAOException e) {
            LOGGER.error("findAll(" + page + ")", e);
            throw new CompanyServiceException(e);
        }
    }

    @Override
    public Optional<Company> findById(long id) {
        try {
            return companyDAO.findById(id);
        } catch (CompanyServiceException e) {
            LOGGER.error("findById(" + id + ")", e);
            throw new CompanyServiceException(e);
        }
    }

    @Override
    public List<Company> findAll() {
        try {
            return companyDAO.findAll();
        } catch (CompanyDAOException e) {
            LOGGER.error("findAll()", e);
            throw new CompanyServiceException(e);
        }
    }

    @Override
    @Transactional
    public void delete(long id) {
        try {
            computerDAO.deleteByMannufacturerId(id);
            companyDAO.deleteById(id);
        } catch (ComputerDAOException | CompanyDAOException e) {
            LOGGER.error("delete(" + id + ")", e);
            throw new CompanyServiceException(e);
        }
    }
}
