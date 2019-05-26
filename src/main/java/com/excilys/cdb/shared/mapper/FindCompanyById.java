package com.excilys.cdb.shared.mapper;

import com.excilys.cdb.model.Company;

import java.util.Optional;

public interface FindCompanyById {
    Optional<Company> findById(long id);
}
