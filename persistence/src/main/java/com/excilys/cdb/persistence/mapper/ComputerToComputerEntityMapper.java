package com.excilys.cdb.persistence.mapper;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.entity.CompanyEntity;
import com.excilys.cdb.persistence.entity.ComputerEntity;
import com.excilys.cdb.persistence.entity.ComputerEntity.ComputerEntityBuilder;
import com.excilys.cdb.shared.mapper.Mapper;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Optional;

@Component
public class ComputerToComputerEntityMapper implements Mapper<Computer, ComputerEntity> {

    private final Mapper<Company, CompanyEntity> companyToCompanyEntityMapper;

    public ComputerToComputerEntityMapper(Mapper<Company, CompanyEntity> companyToCompanyEntityMapper) {
        this.companyToCompanyEntityMapper = companyToCompanyEntityMapper;
    }

    @Override
    public ComputerEntity map(Computer computer) {
        final ComputerEntityBuilder builder = ComputerEntity.builder().id(computer.getId()).name(computer.getName());
        if (Objects.nonNull(computer.getIntroduced())) {
            builder.introduced(Timestamp.valueOf(computer.getIntroduced().atTime(LocalTime.MIDNIGHT)));
        }
        if (Objects.nonNull(computer.getDiscontinued())) {
            builder.discontinued(Timestamp.valueOf(computer.getDiscontinued().atTime(LocalTime.MIDNIGHT)));
        }

        Optional.ofNullable(computer.getManufacturer()).map(companyToCompanyEntityMapper::map)
                .ifPresent(builder::manufacturer);

        return builder.build();
    }
}
