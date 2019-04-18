package com.controller.mapper;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.function.Function;

import com.business.dto.CreateComputerDTO;

public class CreateComputerDTOUiToBusiness implements Function<com.ui.dto.CreateComputerDTO, CreateComputerDTO> {

    @Override
    public CreateComputerDTO apply(com.ui.dto.CreateComputerDTO dtoUi) {
	com.business.dto.CreateComputerDTO dtoMetier = new com.business.dto.CreateComputerDTO();

	dtoMetier.setName(dtoUi.getName());
	try {
	    dtoMetier.setMannufacturerId(Long.valueOf(dtoUi.getMannufacturer()));
	} catch (NumberFormatException e) {
	}

	try {
	    dtoMetier.setIntroduced(LocalDate.parse(dtoUi.getIntroduced()));
	} catch (DateTimeException e) {
	}

	try {
	    dtoMetier.setDiscontinued(LocalDate.parse(dtoUi.getDiscontinued()));
	} catch (DateTimeException e) {
	}

	return dtoMetier;
    }

}
