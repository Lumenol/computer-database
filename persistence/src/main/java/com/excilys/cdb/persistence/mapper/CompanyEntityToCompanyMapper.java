package com.excilys.cdb.persistence.mapper;

import org.springframework.stereotype.Component;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.entity.CompanyEntity;
import com.excilys.cdb.shared.mapper.Mapper;

@Component
public class CompanyEntityToCompanyMapper implements Mapper<CompanyEntity, Company> {
    @Override
    public Company map(CompanyEntity companyEntity) {
	return Company.builder().id(companyEntity.getId()).name(companyEntity.getName()).build();
    }
}
