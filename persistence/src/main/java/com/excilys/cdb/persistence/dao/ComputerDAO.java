package com.excilys.cdb.persistence.dao;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.shared.pagination.Pageable;

import java.util.List;
import java.util.Optional;

public interface ComputerDAO {
    long count();

    long countByNameOrCompanyName(String name);

    long create(Computer computer);

    void deleteById(long id);

    void deleteBymanufacturerId(long id);

    boolean exist(long id);

    List<Computer> findAll(Pageable pageable);

    Optional<Computer> findById(long id);

    List<Computer> searchByNameOrCompanyName(Pageable pageable, String name);

    void update(Computer computer);
}
