package com.metier.service;

import java.util.List;
import java.util.stream.Collectors;

import com.metier.dao.CompanyDAO;
import com.metier.dto.CompanyDTO;
import com.metier.entite.Company;

public class CompanyServiceImpl implements CompanyService {
    private final CompanyDAO companyDao;

    public CompanyServiceImpl(CompanyDAO companyDao) {
	super();
	this.companyDao = companyDao;
    }

    private static CompanyDTO toCompanyDTO(Company company) {
	CompanyDTO companyDTO = new CompanyDTO();
	companyDTO.setId(company.getId());
	companyDTO.setName(company.getName());
	return companyDTO;
    }

    @Override
    public List<CompanyDTO> findAll() {
	return companyDao.findAll().stream().map(CompanyServiceImpl::toCompanyDTO).collect(Collectors.toList());
    }

}
