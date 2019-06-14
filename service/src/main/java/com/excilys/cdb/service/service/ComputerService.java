package com.excilys.cdb.service.service;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.shared.pagination.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Optional;

@PreAuthorize("isAuthenticated()")
public interface ComputerService {
    long count();

    void create(Computer computer);

    void delete(long id);

    boolean exist(long id);

    List<Computer> findAll(Pageable pageable);

    Optional<Computer> findById(long id);

    void update(Computer computer);

    List<Computer> searchByNameOrCompanyName(Pageable pageable, String name);

    long countByNameOrCompanyName(String name);
}
