package com.excilys.cdb.shared.mapper;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.shared.dto.CreateCompanyDTO;
import org.springframework.stereotype.Component;

@Component
public class CreateCompanyDTOToCompanyMapper implements Mapper<CreateCompanyDTO, Company> {

    @Override
    public Company map(CreateCompanyDTO createCompanyDTO) {
        return Company.builder().name(createCompanyDTO.getName()).build();
    }
}
