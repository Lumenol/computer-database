package com.metier.mapper;

import java.util.Objects;
import java.util.function.Function;

import com.metier.dto.CompagnyDTO;
import com.metier.dto.ComputerDTO;
import com.metier.entite.Computer;

public class ComputerToComputerDTOMapper implements Function<Computer, ComputerDTO> {

    @Override
    public ComputerDTO apply(Computer computer) {
	ComputerDTO computerDTO = new ComputerDTO();
	computerDTO.setId(computer.getId());
	computerDTO.setName(computer.getName());
	computerDTO.setIntroduced(computer.getIntroduced());
	computerDTO.setDiscontinued(computer.getDiscontinued().orElse(null));

	if (Objects.nonNull(computer.getManufacturer())) {
	    CompagnyDTO mannufacturer = new CompagnyDTO();
	    mannufacturer.setName(computer.getManufacturer().getName());
	    computerDTO.setMannufacturer(mannufacturer);
	}

	return computerDTO;
    }

}
