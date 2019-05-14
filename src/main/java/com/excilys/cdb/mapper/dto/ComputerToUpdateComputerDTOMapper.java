package com.excilys.cdb.mapper.dto;

import java.util.Objects;

import org.springframework.stereotype.Component;

import com.excilys.cdb.dto.UpdateComputerDTO;
import com.excilys.cdb.model.Computer;

@Component
public class ComputerToUpdateComputerDTOMapper implements Mapper<Computer, UpdateComputerDTO> {

    @Override
    public UpdateComputerDTO map(Computer computer) {
	Objects.requireNonNull(computer);
	final UpdateComputerDTO.UpdateComputerDTOBuilder builder = UpdateComputerDTO.builder().id(computer.getId())
		.name(computer.getName()).introduced(computer.getIntroduced()).discontinued(computer.getDiscontinued());

	if (Objects.nonNull(computer.getManufacturer())) {
	    builder.mannufacturerId(computer.getManufacturer().getId());
	}
	return builder.build();
    }
}
