package com.excilys.cdb.persistence.dao;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.shared.pagination.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CompanyDAO {
    long count();

    @Transactional
    void deleteById(long id);

    boolean exist(long id);

    List<Company> findAll();

    List<Company> findAll(Page page);

    Optional<Company> findById(long id);
}
