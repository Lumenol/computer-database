package com.excilys.cdb.mapper;

import java.util.function.Function;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.ui.dto.CompanyListDTO;

public class CompanyDTOToCompanyListDTO implements Function<CompanyDTO, CompanyListDTO> {

    @Override
    public CompanyListDTO apply(CompanyDTO company) {
	CompanyListDTO companyListDTO = new CompanyListDTO();
	companyListDTO.setId(Long.toString(company.getId()));
	companyListDTO.setName(company.getName());
	return companyListDTO;
    }

}
