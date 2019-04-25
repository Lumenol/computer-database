package com.excilys.cdb.service;

import com.excilys.cdb.dao.ComputerDAO;
import com.excilys.cdb.exception.ComputerDAOException;
import com.excilys.cdb.exception.ComputerServiceException;
import com.excilys.cdb.model.Computer;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ComputerService {


    private static ComputerService instance;
    private final ComputerDAO computerDAO = ComputerDAO.getInstance();

    private ComputerService() {
    }

    public static ComputerService getInstance() {
        if (Objects.isNull(instance)) {
            instance = new ComputerService();
        }
        return instance;
    }

    public void create(Computer computer) {
        try {
            computerDAO.create(computer);
        } catch (ComputerDAOException e) {
            throw new ComputerServiceException(e);
        }
    }

    public void delete(long id) {
        try {
            computerDAO.deleteById(id);
        } catch (ComputerDAOException e) {
            throw new ComputerServiceException(e);
        }
    }

    public List<Computer> findAll(long offset, long limit) {
        try {
            return computerDAO.findAll(offset, limit);
        } catch (ComputerDAOException e) {
            throw new ComputerServiceException(e);
        }
    }

    public Optional<Computer> findById(long id) {
        try {
            return computerDAO.findById(id);
        } catch (ComputerDAOException e) {
            throw new ComputerServiceException(e);
        }
    }

    public void update(Computer computer) {
        try {
            computerDAO.update(computer);
        } catch (ComputerDAOException e) {
            throw new ComputerServiceException(e);
        }
    }

    public long count() {
        try {
            return computerDAO.count();
        } catch (ComputerDAOException e) {
            throw new ComputerServiceException(e);
        }
    }

    public boolean exist(long id) {
        try {
            return findById(id).isPresent();
        } catch (ComputerDAOException e) {
            throw new ComputerServiceException(e);
        }
    }
}
