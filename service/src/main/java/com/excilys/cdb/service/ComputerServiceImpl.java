package com.excilys.cdb.service;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.dao.ComputerDAO;
import com.excilys.cdb.persistence.exception.ComputerDAOException;
import com.excilys.cdb.service.exception.ComputerServiceException;
import com.excilys.cdb.shared.pagination.Pageable;
import com.excilys.cdb.shared.validator.ComputerExistById;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ComputerServiceImpl implements ComputerExistById, ComputerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ComputerService.class);
    private final ComputerDAO computerDAO;

    public ComputerServiceImpl(ComputerDAO computerDAO) {
        super();
        this.computerDAO = computerDAO;
    }

    @Override
    public long count() {
        try {
            return computerDAO.count();
        } catch (ComputerDAOException e) {
            LOGGER.error("count()", e);
            throw new ComputerServiceException(e);
        }
    }

    @Override
    @Transactional
    public void create(Computer computer) {
        try {
            computerDAO.create(computer);
        } catch (ComputerDAOException e) {
            LOGGER.error("create(" + computer + ")", e);
            throw new ComputerServiceException(e);
        }
    }

    @Override
    @Transactional
    public void delete(long id) {
        try {
            computerDAO.deleteById(id);
        } catch (ComputerDAOException e) {
            LOGGER.error("delete(" + id + ")", e);
            throw new ComputerServiceException(e);
        }
    }

    @Override
    public boolean exist(long id) {
        try {
            return computerDAO.exist(id);
        } catch (ComputerDAOException e) {
            LOGGER.error("exist(" + id + ")", e);
            throw new ComputerServiceException(e);
        }
    }

    @Override
    public List<Computer> findAll(Pageable pageable) {
        try {
            return computerDAO.findAll(pageable);
        } catch (ComputerDAOException e) {
            LOGGER.error("findAll(" + pageable + ")", e);
            throw new ComputerServiceException(e);
        }
    }

    @Override
    public Optional<Computer> findById(long id) {
        try {
            return computerDAO.findById(id);
        } catch (ComputerDAOException e) {
            LOGGER.error("findById(" + id + ")", e);
            throw new ComputerServiceException(e);
        }
    }

    @Override
    @Transactional
    public void update(Computer computer) {
        try {
            computerDAO.update(computer);
        } catch (ComputerDAOException e) {
            LOGGER.error("update(" + computer + ")", e);
            throw new ComputerServiceException(e);
        }
    }

    @Override
    public List<Computer> searchByNameOrCompanyName(Pageable pageable, String name) {
        try {
            return computerDAO.searchByNameOrCompanyName(pageable, name);
        } catch (ComputerDAOException e) {
            LOGGER.error("searchByNameOrCompanyName(" + pageable + "," + name + ")", e);
            throw new ComputerServiceException(e);
        }
    }

    @Override
    public long countByNameOrCompanyName(String name) {
        try {
            return computerDAO.countByNameOrCompanyName(name);
        } catch (ComputerDAOException e) {
            LOGGER.error("countByNameOrCompanyName(" + name + ")", e);
            throw new ComputerServiceException(e);
        }
    }
}
