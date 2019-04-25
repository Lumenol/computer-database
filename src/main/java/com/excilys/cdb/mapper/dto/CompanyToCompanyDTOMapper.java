package com.excilys.cdb.mapper.dto;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.model.Company;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CompanyToCompanyDTOMapper implements Mapper<Company, CompanyDTO> {

    private static CompanyToCompanyDTOMapper instance;
    private CompanyToCompanyDTOMapper() {
    }

    public static CompanyToCompanyDTOMapper getInstance() {
        if (Objects.isNull(instance)) {
            instance = new CompanyToCompanyDTOMapper();
        }
        return instance;
    }

    @Override
    public CompanyDTO map(Company company) {
        CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.setId(company.getId());
        companyDTO.setName(company.getName());
        return companyDTO;
    }

}
