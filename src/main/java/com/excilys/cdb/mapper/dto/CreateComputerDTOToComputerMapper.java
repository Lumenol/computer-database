package com.excilys.cdb.mapper.dto;

import com.excilys.cdb.dto.CreateComputerDTO;
import com.excilys.cdb.exception.CompanyServiceException;
import com.excilys.cdb.exception.MapperException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Computer.ComputerBuilder;
import com.excilys.cdb.service.CompanyService;

import java.util.Objects;
import java.util.Optional;

public class CreateComputerDTOToComputerMapper implements Mapper<CreateComputerDTO, Computer> {

    private static CreateComputerDTOToComputerMapper instance;

    private CreateComputerDTOToComputerMapper() {
    }

    public static CreateComputerDTOToComputerMapper getInstance() {
        if (Objects.isNull(instance)) {
            instance = new CreateComputerDTOToComputerMapper();
        }
        return instance;
    }

    @Override
    public Computer map(CreateComputerDTO dto) {
        ComputerBuilder builder = Computer.builder().name(dto.getName()).introduced(dto.getIntroduced())
                .discontinued(dto.getDiscontinued());
        if (Objects.nonNull(dto.getMannufacturerId())) {
            try {
                final Optional<Company> company = CompanyService.getInstance().findById(dto.getMannufacturerId());
                builder.manufacturer(company.orElse(null));
            } catch (CompanyServiceException e) {
                throw new MapperException(e);
            }
        }
        return builder.build();
    }

}
