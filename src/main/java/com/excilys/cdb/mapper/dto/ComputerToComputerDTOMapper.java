package com.excilys.cdb.mapper.dto;

import java.time.LocalDate;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.model.Computer;

@Component
public class ComputerToComputerDTOMapper implements Mapper<Computer, ComputerDTO> {

    private String toString(LocalDate date) {
	return Objects.toString(date, null);
    }

    @Override
    public ComputerDTO map(Computer computer) {
	Objects.requireNonNull(computer);
	final ComputerDTO.ComputerDTOBuilder builder = ComputerDTO.builder().id(computer.getId())
		.name(computer.getName()).introduced(toString(computer.getIntroduced()))
		.discontinued(toString(computer.getDiscontinued()));

	if (Objects.nonNull(computer.getManufacturer())) {
	    builder.mannufacturer(computer.getManufacturer().getName());
	}
	return builder.build();
    }

}
