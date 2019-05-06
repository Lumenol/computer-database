package com.excilys.cdb.mapper.dto;

import java.util.Objects;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.model.Company;

public class CompanyToCompanyDTOMapper implements Mapper<Company, CompanyDTO> {

    private static CompanyToCompanyDTOMapper instance;

    private CompanyToCompanyDTOMapper() {
    }

    public static synchronized CompanyToCompanyDTOMapper getInstance() {
	if (Objects.isNull(instance)) {
	    instance = new CompanyToCompanyDTOMapper();
	}
	return instance;
    }

    @Override
    public CompanyDTO map(Company company) {
	Objects.requireNonNull(company);
	return CompanyDTO.builder().id(company.getId()).name(company.getName()).build();
    }

}
