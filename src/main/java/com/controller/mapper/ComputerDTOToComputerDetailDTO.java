package com.controller.mapper;

import java.util.Objects;
import java.util.function.Function;

import com.business.dto.CompanyDTO;
import com.business.dto.ComputerDTO;
import com.ui.dto.ComputerDetailDTO;

public class ComputerDTOToComputerDetailDTO implements Function<ComputerDTO, ComputerDetailDTO> {

    @Override
    public ComputerDetailDTO apply(ComputerDTO computer) {
	ComputerDetailDTO computerDetailDTO = new ComputerDetailDTO();
	computerDetailDTO.setId(Long.toString(computer.getId()));
	computerDetailDTO.setName(computer.getName());

	if (Objects.nonNull(computer.getIntroduced())) {
	    computerDetailDTO.setIntroduced(computer.getIntroduced().toString());
	}

	if (Objects.nonNull(computer.getDiscontinued())) {
	    computerDetailDTO.setIntroduced(computer.getDiscontinued().toString());
	}

	if (Objects.nonNull(computer.getMannufacturer())) {
	    computerDetailDTO.setMannufacturer(computer.getMannufacturer().map(CompanyDTO::getName).orElse(null));
	}

	return computerDetailDTO;
    }

}
