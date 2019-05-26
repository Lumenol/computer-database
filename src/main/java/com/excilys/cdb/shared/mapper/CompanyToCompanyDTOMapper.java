package com.excilys.cdb.shared.mapper;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.shared.dto.CompanyDTO;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CompanyToCompanyDTOMapper implements Mapper<Company, CompanyDTO> {

    @Override
    public CompanyDTO map(Company company) {
        Objects.requireNonNull(company);
        return CompanyDTO.builder().id(company.getId()).name(company.getName()).build();
    }

}
