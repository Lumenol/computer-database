package com.excilys.cdb.mapper;

import java.util.function.Function;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.model.Company;

public class CompanyToCompanyDTOMapper implements Function<Company, CompanyDTO> {

    @Override
    public CompanyDTO apply(Company company) {
	CompanyDTO companyDTO = new CompanyDTO();
	companyDTO.setId(company.getId());
	companyDTO.setName(company.getName());
	return companyDTO;
    }

}
