package com.excilys.cdb.shared.mapper;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.shared.dto.UpdateCompanyDTO;
import org.springframework.stereotype.Component;

@Component
public class UpdateCompanyDTOToCompanyMapper implements Mapper<UpdateCompanyDTO, Company> {

    @Override
    public Company map(UpdateCompanyDTO updateCompanyDTO) {
        return Company.builder().id(updateCompanyDTO.getId()).name(updateCompanyDTO.getName()).build();
    }
}
