package com.metier.service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.metier.dao.CompanyDAO;
import com.metier.dto.CompanyDTO;
import com.metier.entite.Company;

public class CompanyServiceImpl implements CompanyService {
    private final CompanyDAO companyDao;
    private final Function<Company, CompanyDTO> companyToCompanyDTO;

    public CompanyServiceImpl(CompanyDAO companyDao, Function<Company, CompanyDTO> companyToCompanyDTO) {
	super();
	this.companyDao = companyDao;
	this.companyToCompanyDTO = companyToCompanyDTO;
    }

    @Override
    public List<CompanyDTO> findAll() {
	return companyDao.findAll().stream().map(companyToCompanyDTO::apply).collect(Collectors.toList());
    }

}
