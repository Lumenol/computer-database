package com.excilys.cdb.mapper.dto;

import com.excilys.cdb.dto.UpdateComputerDTO;
import com.excilys.cdb.exception.CompanyServiceException;
import com.excilys.cdb.exception.MapperException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.CompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class UpdateComputerDTOToComputerMapper implements Mapper<UpdateComputerDTO, Computer> {

    private static UpdateComputerDTOToComputerMapper instance;
    private final CompanyService companyService = CompanyService.getInstance();
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private UpdateComputerDTOToComputerMapper() {
    }

    public static UpdateComputerDTOToComputerMapper getInstance() {
        if (Objects.isNull(instance)) {
            instance = new UpdateComputerDTOToComputerMapper();
        }
        return instance;
    }

    @Override
    public Computer map(UpdateComputerDTO dto) {
        final Computer.ComputerBuilder builder = Computer.builder().id(dto.getId()).name(dto.getName())
                .introduced(dto.getIntroduced()).discontinued(dto.getDiscontinued());
        if (Objects.nonNull(dto.getMannufacturerId())) {
            try {
                Company mannufacturer = companyService.findById(dto.getMannufacturerId()).orElse(null);
                builder.manufacturer(mannufacturer);
            } catch (CompanyServiceException e) {
                logger.warn("map(" + dto + ")", e);
                throw new MapperException(e);
            }
        }
        return builder.build();
    }

}
