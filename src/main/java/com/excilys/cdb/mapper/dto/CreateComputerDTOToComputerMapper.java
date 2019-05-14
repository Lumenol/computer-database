package com.excilys.cdb.mapper.dto;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.excilys.cdb.dto.CreateComputerDTO;
import com.excilys.cdb.exception.CompanyServiceException;
import com.excilys.cdb.exception.MapperException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Computer.ComputerBuilder;
import com.excilys.cdb.service.CompanyService;

@Component
public class CreateComputerDTOToComputerMapper implements Mapper<CreateComputerDTO, Computer> {

    private final CompanyService companyService;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public CreateComputerDTOToComputerMapper(CompanyService companyService) {
	this.companyService = companyService;
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
	Objects.requireNonNull(dto);
	try {
	    ComputerBuilder builder = Computer.builder().name(dto.getName().trim()).introduced(dto.getIntroduced())
		    .discontinued(dto.getDiscontinued());

	    Optional.ofNullable(dto.getMannufacturerId()).flatMap(companyService::findById)
		    .ifPresent(builder::manufacturer);

	    return builder.build();
	} catch (CompanyServiceException | DateTimeParseException | NumberFormatException e) {
	    logger.warn("map(" + dto + ")", e);
	    throw new MapperException(e);
	}
    }
}
