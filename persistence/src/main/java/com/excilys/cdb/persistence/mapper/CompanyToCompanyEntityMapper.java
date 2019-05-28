package com.excilys.cdb.persistence.mapper;

import org.springframework.stereotype.Component;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.entity.CompanyEntity;
import com.excilys.cdb.shared.mapper.Mapper;

@Component
public class CompanyToCompanyEntityMapper implements Mapper<Company, CompanyEntity> {
    @Override
    public CompanyEntity map(Company company) {
	return CompanyEntity.builder().id(company.getId()).name(company.getName()).build();
    }
}
