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

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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

    private boolean isBlank(String s) {
        return Objects.isNull(s) || s.trim().isEmpty();
    }

    private LocalDate parseDate(String date) {
        return isBlank(date) ? null : LocalDate.parse(date);
    }

    private Long parseId(String id) {
        return isBlank(id) ? null : Long.valueOf(id);
    }

    @Override
    public Computer map(CreateComputerDTO dto) {
        try {
            ComputerBuilder builder = Computer.builder().name(dto.getName()).introduced(parseDate(dto.getIntroduced())).discontinued(parseDate(dto.getDiscontinued()));

            final Long mannufacturerID = parseId(dto.getMannufacturerId());
            if (Objects.nonNull(mannufacturerID)) {
                final Optional<Company> company = companyService.findById(mannufacturerID);
                builder.manufacturer(company.orElse(null));
            }
            return builder.build();
        } catch (CompanyServiceException | DateTimeParseException | NumberFormatException e) {
            logger.warn("map(" + dto + ")", e);
            throw new MapperException(e);
        }
    }
}
