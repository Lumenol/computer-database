package com.business.dao;

import java.util.List;
import java.util.Optional;

import com.business.entite.Company;

public interface CompanyDAO {
    List<Company> findAll();

    Optional<Company> findById(long id);
}
