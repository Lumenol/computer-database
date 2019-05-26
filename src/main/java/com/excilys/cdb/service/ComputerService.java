package com.excilys.cdb.service;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.shared.pagination.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ComputerService {
    long count();

    @Transactional
    void create(Computer computer);

    @Transactional
    void delete(long id);

    boolean exist(long id);

    List<Computer> findAll(Pageable pageable);

    Optional<Computer> findById(long id);

    @Transactional
    void update(Computer computer);

    List<Computer> searchByNameOrCompanyName(Pageable pageable, String name);

    long countByNameOrCompanyName(String name);
}
