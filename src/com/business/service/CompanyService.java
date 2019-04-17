package com.business.service;

import java.util.List;

import com.business.dto.CompanyDTO;

public interface CompanyService {
    List<CompanyDTO> findAll();
}
