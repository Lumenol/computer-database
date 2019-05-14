package com.excilys.cdb.mapper.dto;

import java.time.LocalDate;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.excilys.cdb.dto.UpdateComputerDTO;
import com.excilys.cdb.model.Computer;

@Component
public class ComputerToUpdateComputerDTOMapper implements Mapper<Computer, UpdateComputerDTO> {

    private String toString(LocalDate date) {
	return Objects.toString(date, null);
    }

    @Override
    public UpdateComputerDTO map(Computer computer) {
	Objects.requireNonNull(computer);
	final UpdateComputerDTO.UpdateComputerDTOBuilder builder = UpdateComputerDTO.builder()
		.id(computer.getId().toString()).name(computer.getName()).introduced(toString(computer.getIntroduced()))
		.discontinued(toString(computer.getDiscontinued()));

	if (Objects.nonNull(computer.getManufacturer())) {
	    builder.mannufacturerId(computer.getManufacturer().getId().toString());
	}
	return builder.build();
    }
}
