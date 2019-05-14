package com.excilys.cdb.mapper.dto;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.excilys.cdb.dto.UpdateComputerDTO;
import com.excilys.cdb.exception.CompanyServiceException;
import com.excilys.cdb.exception.MapperException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.CompanyService;

@Component
public class UpdateComputerDTOToComputerMapper implements Mapper<UpdateComputerDTO, Computer> {

    private final CompanyService companyService;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public UpdateComputerDTOToComputerMapper(CompanyService companyService) {
	this.companyService = companyService;
    }

    private boolean isBlank(String s) {
	return Objects.isNull(s) || s.trim().isEmpty();
    }

    private LocalDate parseDate(String date) {
	return isBlank(date) ? null : LocalDate.parse(date);
    }

    private Long parseMannufacturerId(String id) {
	return isBlank(id) ? null : Long.valueOf(id);
    }

    private long parseId(String id) {
	return Long.parseLong(id);
    }

    @Override
    public Computer map(UpdateComputerDTO dto) {
	Objects.requireNonNull(dto);
	try {
	    final Computer.ComputerBuilder builder = Computer.builder().id(dto.getId()).name(dto.getName().trim())
		    .introduced(dto.getIntroduced()).discontinued(dto.getDiscontinued());
	    Optional.ofNullable(dto.getMannufacturerId()).flatMap(companyService::findById)
		    .ifPresent(builder::manufacturer);
	    return builder.build();
	} catch (CompanyServiceException | DateTimeParseException | NumberFormatException e) {
	    logger.warn("map(" + dto + ")", e);
	    throw new MapperException(e);
	}
    }
}
