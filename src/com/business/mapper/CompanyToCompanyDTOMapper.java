package com.business.mapper;

import java.util.function.Function;

import com.business.dto.CompanyDTO;
import com.business.entite.Company;

public class CompanyToCompanyDTOMapper implements Function<Company, CompanyDTO> {

    @Override
    public CompanyDTO apply(Company company) {
	CompanyDTO companyDTO = new CompanyDTO();
	companyDTO.setId(company.getId());
	companyDTO.setName(company.getName());
	return companyDTO;
    }

}
