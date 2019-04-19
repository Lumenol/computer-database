package com.excilys.cdb.mapper;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.function.Function;

import com.excilys.cdb.dto.CreateComputerDTO;

public class CreateComputerDTOUiToBusiness implements Function<com.excilys.cdb.ui.dto.CreateComputerDTO, CreateComputerDTO> {

    @Override
    public CreateComputerDTO apply(com.excilys.cdb.ui.dto.CreateComputerDTO dtoUi) {
	com.excilys.cdb.dto.CreateComputerDTO dtoMetier = new com.excilys.cdb.dto.CreateComputerDTO();

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
