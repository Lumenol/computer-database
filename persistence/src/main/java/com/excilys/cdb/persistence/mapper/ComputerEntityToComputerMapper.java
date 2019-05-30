package com.excilys.cdb.persistence.mapper;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Computer.ComputerBuilder;
import com.excilys.cdb.persistence.entity.CompanyEntity;
import com.excilys.cdb.persistence.entity.ComputerEntity;
import com.excilys.cdb.shared.mapper.Mapper;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class ComputerEntityToComputerMapper implements Mapper<ComputerEntity, Computer> {

    private final Mapper<CompanyEntity, Company> companyEntityToCompanyMapper;

    public ComputerEntityToComputerMapper(Mapper<CompanyEntity, Company> companyEntityToCompanyMapper) {
	this.companyEntityToCompanyMapper = companyEntityToCompanyMapper;
    }

    @Override
    public Computer map(ComputerEntity computerEntity) {
        final ComputerBuilder builder = Computer.builder().id(computerEntity.getId()).name(computerEntity.getName());
        if (Objects.nonNull(computerEntity.getIntroduced())) {
            builder.introduced(computerEntity.getIntroduced().toLocalDateTime().toLocalDate());
        }
        if (Objects.nonNull(computerEntity.getDiscontinued())) {
            builder.discontinued(computerEntity.getDiscontinued().toLocalDateTime().toLocalDate());
        }

	Optional.ofNullable(computerEntity.getManufacturer()).map(companyEntityToCompanyMapper::map)
		.ifPresent(builder::manufacturer);

	return builder.build();
    }
}
