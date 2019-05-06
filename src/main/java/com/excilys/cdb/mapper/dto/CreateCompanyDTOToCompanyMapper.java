package com.excilys.cdb.mapper.dto;

import java.util.Objects;

import com.excilys.cdb.dto.CreateCompanyDTO;
import com.excilys.cdb.model.Company;

public class CreateCompanyDTOToCompanyMapper implements Mapper<CreateCompanyDTO, Company> {

    private static CreateCompanyDTOToCompanyMapper instance;

    private CreateCompanyDTOToCompanyMapper() {
    }

    public static synchronized CreateCompanyDTOToCompanyMapper getInstance() {
	if (Objects.isNull(instance)) {
	    instance = new CreateCompanyDTOToCompanyMapper();
	}
	return instance;
    }

    @Override
    public Company map(CreateCompanyDTO dto) {
	Objects.requireNonNull(dto);
	return Company.builder().name(dto.getName().trim()).build();
    }
}
