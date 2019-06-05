package com.excilys.cdb.service.service;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.dao.ComputerDAO;
import com.excilys.cdb.service.exception.ComputerServiceException;
import com.excilys.cdb.shared.logexception.LogAndWrapException;
import com.excilys.cdb.shared.pagination.Pageable;
import com.excilys.cdb.shared.validator.ComputerExistById;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ComputerServiceImpl implements ComputerExistById, ComputerService {

    private final ComputerDAO computerDAO;

    public ComputerServiceImpl(ComputerDAO computerDAO) {
        super();
        this.computerDAO = computerDAO;
    }

    @Override
    @LogAndWrapException(logger = ComputerService.class, exception = ComputerServiceException.class)
    public long count() {
        return computerDAO.count();
    }

    @Override
    @Transactional
    @LogAndWrapException(logger = ComputerService.class, exception = ComputerServiceException.class)
    public void create(Computer computer) {
        computerDAO.create(computer);
    }

    @Override
    @Transactional
    @LogAndWrapException(logger = ComputerService.class, exception = ComputerServiceException.class)
    public void delete(long id) {
        computerDAO.deleteById(id);
    }

    @Override
    @LogAndWrapException(logger = ComputerService.class, exception = ComputerServiceException.class)
    public boolean exist(long id) {
        return computerDAO.exist(id);
    }

    @Override
    @LogAndWrapException(logger = ComputerService.class, exception = ComputerServiceException.class)
    public List<Computer> findAll(Pageable pageable) {
        return computerDAO.findAll(pageable);
    }

    @Override
    @LogAndWrapException(logger = ComputerService.class, exception = ComputerServiceException.class)
    public Optional<Computer> findById(long id) {
        return computerDAO.findById(id);
    }

    @Override
    @Transactional
    @LogAndWrapException(logger = ComputerService.class, exception = ComputerServiceException.class)
    public void update(Computer computer) {
        computerDAO.update(computer);
    }

    @Override
    @LogAndWrapException(logger = ComputerService.class, exception = ComputerServiceException.class)
    public List<Computer> searchByNameOrCompanyName(Pageable pageable, String name) {
        return computerDAO.searchByNameOrCompanyName(pageable, name);
    }

    @Override
    @LogAndWrapException(logger = ComputerService.class, exception = ComputerServiceException.class)
    public long countByNameOrCompanyName(String name) {
        return computerDAO.countByNameOrCompanyName(name);
    }
}
