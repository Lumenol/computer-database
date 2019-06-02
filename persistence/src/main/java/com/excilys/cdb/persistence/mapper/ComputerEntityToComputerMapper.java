package com.excilys.cdb.persistence.mapper;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Computer.ComputerBuilder;
import com.excilys.cdb.persistence.entity.CompanyEntity;
import com.excilys.cdb.persistence.entity.ComputerEntity;
import com.excilys.cdb.shared.mapper.Mapper;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
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

        Optional.ofNullable(computerEntity.getIntroduced()).map(Timestamp::toLocalDateTime).map(LocalDateTime::toLocalDate).ifPresent(builder::introduced);
        Optional.ofNullable(computerEntity.getDiscontinued()).map(Timestamp::toLocalDateTime).map(LocalDateTime::toLocalDate).ifPresent(builder::discontinued);

        Optional.ofNullable(computerEntity.getManufacturer()).map(companyEntityToCompanyMapper::map).ifPresent(builder::manufacturer);

        return builder.build();
    }
}
