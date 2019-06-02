package com.excilys.cdb.service.service;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.dao.CompanyDAO;
import com.excilys.cdb.persistence.dao.ComputerDAO;
import com.excilys.cdb.service.exception.CompanyServiceException;
import com.excilys.cdb.shared.logexception.LogAndWrapException;
import com.excilys.cdb.shared.mapper.FindCompanyById;
import com.excilys.cdb.shared.pagination.Page;
import com.excilys.cdb.shared.validator.CompanyExistById;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class CompanyServiceImpl implements CompanyService, CompanyExistById, FindCompanyById {
    private final CompanyDAO companyDAO;
    private final ComputerDAO computerDAO;

    public CompanyServiceImpl(CompanyDAO companyDAO, ComputerDAO computerDAO) {
        this.companyDAO = companyDAO;
        this.computerDAO = computerDAO;
    }

    @Override
    @LogAndWrapException(logger = CompanyService.class, exception = CompanyServiceException.class)
    public long count() {
        return companyDAO.count();
    }

    @Override
    @LogAndWrapException(logger = CompanyService.class, exception = CompanyServiceException.class)
    public boolean exist(long id) {
        return companyDAO.exist(id);
    }

    @Override
    @LogAndWrapException(logger = CompanyService.class, exception = CompanyServiceException.class)
    public List<Company> findAll(Page page) {
        return companyDAO.findAll(page);
    }

    @Override
    @LogAndWrapException(logger = CompanyService.class, exception = CompanyServiceException.class)
    public Optional<Company> findById(long id) {
        return companyDAO.findById(id);
    }

    @Override
    @LogAndWrapException(logger = CompanyService.class, exception = CompanyServiceException.class)
    public List<Company> findAll() {
        return companyDAO.findAll();
    }

    @Override
    @Transactional
    @LogAndWrapException(logger = CompanyService.class, exception = CompanyServiceException.class)
    public void delete(long id) {
        computerDAO.deleteBymanufacturerId(id);
        companyDAO.deleteById(id);
    }
}
