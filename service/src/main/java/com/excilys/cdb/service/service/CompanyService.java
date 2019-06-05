package com.excilys.cdb.service.service;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.shared.pagination.Page;

import java.util.List;
import java.util.Optional;

public interface CompanyService {
    long count();

    boolean exist(long id);

    List<Company> findAll(Page page);

    Optional<Company> findById(long id);

    List<Company> findAll();

    void delete(long id);
}
