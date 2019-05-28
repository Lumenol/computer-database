package com.excilys.cdb.persistence.mapper;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.entity.CompanyEntity;
import com.excilys.cdb.persistence.entity.ComputerEntity;
import com.excilys.cdb.persistence.entity.ComputerEntity.ComputerEntityBuilder;
import com.excilys.cdb.shared.mapper.Mapper;

@Component
public class ComputerToComputerEntityMapper implements Mapper<Computer, ComputerEntity> {

    private final Mapper<Company, CompanyEntity> companyToCompanyEntityMapper;

    public ComputerToComputerEntityMapper(Mapper<Company, CompanyEntity> companyToCompanyEntityMapper) {
	this.companyToCompanyEntityMapper = companyToCompanyEntityMapper;
    }

    @Override
    public ComputerEntity map(Computer computer) {
	final ComputerEntityBuilder builder = ComputerEntity.builder().id(computer.getId()).name(computer.getName())
		.introduced(computer.getIntroduced()).discontinued(computer.getDiscontinued());

	Optional.ofNullable(computer.getManufacturer()).map(companyToCompanyEntityMapper::map)
		.ifPresent(builder::manufacturer);

	return builder.build();
    }
}
