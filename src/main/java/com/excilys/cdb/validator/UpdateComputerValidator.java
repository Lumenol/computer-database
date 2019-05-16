package com.excilys.cdb.validator;

import java.util.Objects;

import org.springframework.stereotype.Component;

import com.excilys.cdb.dto.UpdateComputerDTO;
import com.excilys.cdb.exception.ValidationException;
import com.excilys.cdb.service.ComputerService;

@Component
public class UpdateComputerValidator implements Validator<UpdateComputerDTO> {
    private final ComputerValidatorUtils computerValidatorUtils;
    private final ComputerService computerService;

    public UpdateComputerValidator(ComputerValidatorUtils computerValidatorUtils, ComputerService computerService) {
	super();
	this.computerValidatorUtils = computerValidatorUtils;
	this.computerService = computerService;
    }

    private void checkId(Long id) {
	if (Objects.isNull(id)) {
	    throw new ValidationException("id", "L'id ne peut pas être nul.");
	} else if (!computerService.exist(id)) {
	    throw new ValidationException("id", "L'id n'exist pas.");
	}
    }

    @Override
    public void check(UpdateComputerDTO toValidate) {
	Objects.requireNonNull(toValidate);
	computerValidatorUtils.checkName(toValidate.getName());
	computerValidatorUtils.checkIntroduced(toValidate.getIntroduced());
	computerValidatorUtils.checkDiscontinued(toValidate.getDiscontinued());
	computerValidatorUtils.checkIntroducedIsBeforeDiscontinued(toValidate.getIntroduced(),
		toValidate.getDiscontinued());
	computerValidatorUtils.checkMannufacturerId(toValidate.getMannufacturerId());
	checkId(toValidate.getId());
    }
}
