package com.excilys.cdb.service;

import java.util.List;

import com.excilys.cdb.dto.CompanyDTO;

public interface CompanyService {
    boolean exist(long id);

    List<CompanyDTO> findAll();

    List<CompanyDTO> findAll(long from, long to);

}
