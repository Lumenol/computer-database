package com.metier.mapper;

import java.util.function.Function;

import com.metier.dto.CompanyDTO;
import com.metier.entite.Company;

public class CompanyToCompanyDTOMapper implements Function<Company, CompanyDTO> {

    @Override
    public CompanyDTO apply(Company company) {
	CompanyDTO companyDTO = new CompanyDTO();
	companyDTO.setId(company.getId());
	companyDTO.setName(company.getName());
	return companyDTO;
    }

}
