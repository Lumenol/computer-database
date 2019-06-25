package com.excilys.cdb.service.service;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.shared.pagination.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Optional;

@PreAuthorize("isAuthenticated()")
public interface CompanyService {
    long count();

    boolean exist(long id);

    List<Company> findAll(Page page);

    Optional<Company> findById(long id);

    List<Company> findAll();

    void delete(long id);

    void update(Company company);

    void create(Company company);


}
