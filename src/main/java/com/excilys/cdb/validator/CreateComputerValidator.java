package com.excilys.cdb.validator;

import java.util.Objects;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.excilys.cdb.dto.CreateComputerDTO;

@Component
public class CreateComputerValidator implements Validator {

    private final ComputerValidatorUtils computerValidatorUtils;

    public CreateComputerValidator(ComputerValidatorUtils computerValidatorUtils) {
	this.computerValidatorUtils = computerValidatorUtils;
    }

    @Override
    public boolean supports(Class<?> clazz) {
	return CreateComputerDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
	Objects.requireNonNull(target);
	CreateComputerDTO toValidate = (CreateComputerDTO) target;
	computerValidatorUtils.checkName(toValidate.getName(), errors);
	computerValidatorUtils.checkIntroduced(toValidate.getIntroduced(), errors);
	computerValidatorUtils.checkDiscontinued(toValidate.getDiscontinued(), errors);
	computerValidatorUtils.checkIntroducedIsBeforeDiscontinued(toValidate.getIntroduced(),
		toValidate.getDiscontinued(), errors);
	computerValidatorUtils.checkMannufacturerId(toValidate.getMannufacturerId(), errors);
    }

}
