package com.excilys.cdb.persistence.dao;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.shared.pagination.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ComputerDAO {
    long count();

    @Transactional
    long create(Computer computer);

    @Transactional
    void deleteById(long id);

    @Transactional
    void deleteByMannufacturerId(long id);

    List<Computer> findAll(Pageable pageable);

    Optional<Computer> findById(long id);

    @Transactional
    void update(Computer computer);

    long countByNameOrCompanyName(String name);

    List<Computer> searchByNameOrCompanyName(Pageable pageable, String name);

    boolean exist(long id);
}
