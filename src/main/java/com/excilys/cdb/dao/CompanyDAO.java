package com.excilys.cdb.dao;

import java.util.List;
import java.util.Optional;

import com.excilys.cdb.model.Company;

public interface CompanyDAO {
    List<Company> findAll();

    List<Company> findAll(long from, long to);

    Optional<Company> findById(long id);
}
