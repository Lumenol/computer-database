package com.controller.mapper;

import java.util.function.Function;

import com.business.dto.CompanyDTO;
import com.ui.dto.CompanyListDTO;

public class CompanyDTOToCompanyListDTO implements Function<CompanyDTO, CompanyListDTO> {

    @Override
    public CompanyListDTO apply(CompanyDTO company) {
	CompanyListDTO companyListDTO = new CompanyListDTO();
	companyListDTO.setId(Long.toString(company.getId()));
	companyListDTO.setName(company.getName());
	return companyListDTO;
    }

}
