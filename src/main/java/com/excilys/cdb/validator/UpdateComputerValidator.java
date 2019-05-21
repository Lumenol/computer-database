package com.excilys.cdb.validator;

import java.util.Objects;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.excilys.cdb.dto.UpdateComputerDTO;
import com.excilys.cdb.service.ComputerService;

@Component
public class UpdateComputerValidator implements Validator {
    private final ComputerValidatorUtils computerValidatorUtils;
    private final ComputerService computerService;

    public UpdateComputerValidator(ComputerValidatorUtils computerValidatorUtils, ComputerService computerService) {
	super();
	this.computerValidatorUtils = computerValidatorUtils;
	this.computerService = computerService;
    }

    private void checkId(Long id, Errors errors) {
	if (Objects.isNull(id)) {
	    errors.rejectValue("id", "validator.id.null");
	} else if (!computerService.exist(id)) {
	    errors.rejectValue("id", "validator.id.notFound");
	}
    }

    @Override
    public boolean supports(Class<?> clazz) {
	return UpdateComputerDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
	Objects.requireNonNull(target);
	UpdateComputerDTO toValidate = (UpdateComputerDTO) target;
	computerValidatorUtils.checkName(toValidate.getName(), errors);
	computerValidatorUtils.checkIntroduced(toValidate.getIntroduced(), errors);
	computerValidatorUtils.checkDiscontinued(toValidate.getDiscontinued(), errors);
	computerValidatorUtils.checkIntroducedIsBeforeDiscontinued(toValidate.getIntroduced(),
		toValidate.getDiscontinued(), errors);
	computerValidatorUtils.checkMannufacturerId(toValidate.getMannufacturerId(), errors);
	checkId(toValidate.getId(), errors);
    }
}
