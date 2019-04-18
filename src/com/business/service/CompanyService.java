package com.business.service;

import java.util.List;

import com.business.dto.CompanyDTO;

public interface CompanyService {
    boolean exist(long id);

    List<CompanyDTO> findAll();

    List<CompanyDTO> findAll(long from, long to);

}
