package com.business.dao;

import java.util.List;
import java.util.Optional;

import com.business.entite.Computer;

public interface ComputerDAO {
    long create(Computer computer);

    void deleteById(long id);

    List<Computer> findAll();

    List<Computer> findAll(long from, long to);

    Optional<Computer> findById(long id);

    void update(Computer computer);
}
