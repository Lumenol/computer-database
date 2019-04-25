package com.excilys.cdb.mapper.dto;

import java.util.Objects;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.model.Company;

public class CompanyToCompanyDTOMapper implements Mapper<Company, CompanyDTO> {

    private static CompanyToCompanyDTOMapper instance;

    public static CompanyToCompanyDTOMapper getInstance() {
	if (Objects.isNull(instance)) {
	    instance = new CompanyToCompanyDTOMapper();
	}
	return instance;
    }

    private CompanyToCompanyDTOMapper() {
    }

    @Override
    public CompanyDTO map(Company company) {
	CompanyDTO companyDTO = new CompanyDTO();
	companyDTO.setId(company.getId());
	companyDTO.setName(company.getName());
	return companyDTO;
    }

}
