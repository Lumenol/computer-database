package com.excilys.cdb.persistence.mapper;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Computer.ComputerBuilder;
import com.excilys.cdb.persistence.entity.CompanyEntity;
import com.excilys.cdb.persistence.entity.ComputerEntity;
import com.excilys.cdb.shared.mapper.Mapper;

@Component
public class ComputerEntityToComputerMapper implements Mapper<ComputerEntity, Computer> {

    private final Mapper<CompanyEntity, Company> companyEntityToCompanyMapper;

    public ComputerEntityToComputerMapper(Mapper<CompanyEntity, Company> companyEntityToCompanyMapper) {
	this.companyEntityToCompanyMapper = companyEntityToCompanyMapper;
    }

    @Override
    public Computer map(ComputerEntity computerEntity) {
	final ComputerBuilder builder = Computer.builder().id(computerEntity.getId()).name(computerEntity.getName())
		.introduced(computerEntity.getIntroduced()).discontinued(computerEntity.getDiscontinued());

	Optional.ofNullable(computerEntity.getManufacturer()).map(companyEntityToCompanyMapper::map)
		.ifPresent(builder::manufacturer);

	return builder.build();
    }
}
