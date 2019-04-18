package com.controller.mapper;

import java.util.function.Function;

import com.business.dto.ComputerDTO;
import com.ui.dto.ComputerListDTO;

public class ComputerDTOToComputerListDTO implements Function<ComputerDTO, ComputerListDTO> {

    @Override
    public ComputerListDTO apply(ComputerDTO computer) {
	ComputerListDTO computerListDTO = new ComputerListDTO();
	computerListDTO.setId(Long.toString(computer.getId()));
	computerListDTO.setName(computer.getName());
	return computerListDTO;
    }
}
