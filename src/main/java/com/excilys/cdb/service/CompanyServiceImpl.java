package com.excilys.cdb.service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.excilys.cdb.dao.CompanyDAO;
import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.model.Company;

public class CompanyServiceImpl implements CompanyService {
    private final CompanyDAO companyDao;
    private final Function<Company, CompanyDTO> companyToCompanyDTO;

    public CompanyServiceImpl(CompanyDAO companyDao, Function<Company, CompanyDTO> companyToCompanyDTO) {
	super();
	this.companyDao = companyDao;
	this.companyToCompanyDTO = companyToCompanyDTO;
    }

    @Override
    public boolean exist(long id) {
	return companyDao.findById(id).isPresent();
    }

    @Override
    public List<CompanyDTO> findAll() {
	return mapAll(companyDao.findAll());
    }

    @Override
    public List<CompanyDTO> findAll(long from, long to) {
	return mapAll(companyDao.findAll(from, to));
    }

    private List<CompanyDTO> mapAll(List<Company> companies) {
	return companies.stream().map(companyToCompanyDTO::apply).collect(Collectors.toList());
    }

}
