package com.excilys.cdb.mapper.dto;

import com.excilys.cdb.dto.CreateComputerDTO;
import com.excilys.cdb.exception.CompanyServiceException;
import com.excilys.cdb.exception.MapperException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Computer.ComputerBuilder;
import com.excilys.cdb.service.CompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.Optional;

public class CreateComputerDTOToComputerMapper implements Mapper<CreateComputerDTO, Computer> {

    private static CreateComputerDTOToComputerMapper instance;
    private final CompanyService companyService = CompanyService.getInstance();
    private final Logger logger = LoggerFactory.getLogger(getClass());

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
                final Optional<Company> company = companyService.findById(dto.getMannufacturerId());
                builder.manufacturer(company.orElse(null));
            } catch (CompanyServiceException e) {
                logger.warn("map(" + dto + ")", e);
                throw new MapperException(e);
            }
        }
        return builder.build();
    }

}
