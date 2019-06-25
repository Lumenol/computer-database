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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    void delete(long id);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    void update(Company company);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    void create(Company company);


}
