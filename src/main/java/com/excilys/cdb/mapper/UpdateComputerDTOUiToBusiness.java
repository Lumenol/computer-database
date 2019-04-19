package com.excilys.cdb.mapper;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.function.Function;

import com.excilys.cdb.dto.UpdateComputerDTO;

public class UpdateComputerDTOUiToBusiness implements Function<com.excilys.cdb.ui.dto.UpdateComputerDTO, UpdateComputerDTO> {

    @Override
    public UpdateComputerDTO apply(com.excilys.cdb.ui.dto.UpdateComputerDTO dtoUi) {

	UpdateComputerDTO dtoMetier = new UpdateComputerDTO();

	dtoMetier.setId(dtoUi.getId());
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
	    dtoMetier.setDiscontinued((LocalDate.parse(dtoUi.getDiscontinued())));
	} catch (DateTimeException e) {
	}
	return dtoMetier;
    }

}
