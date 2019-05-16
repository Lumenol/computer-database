package com.excilys.cdb.validator;

import java.util.Objects;

import org.springframework.stereotype.Component;

import com.excilys.cdb.dto.CreateComputerDTO;

@Component
public class CreateComputerValidator implements Validator<CreateComputerDTO> {

    private final ComputerValidatorUtils computerValidatorUtils;

    public CreateComputerValidator(ComputerValidatorUtils computerValidatorUtils) {
	this.computerValidatorUtils = computerValidatorUtils;
    }

    @Override
    public void check(CreateComputerDTO toValidate) {
	Objects.requireNonNull(toValidate);
	computerValidatorUtils.checkName(toValidate.getName());
	computerValidatorUtils.checkIntroduced(toValidate.getIntroduced());
	computerValidatorUtils.checkDiscontinued(toValidate.getDiscontinued());
	computerValidatorUtils.checkIntroducedIsBeforeDiscontinued(toValidate.getIntroduced(),
		toValidate.getDiscontinued());
	computerValidatorUtils.checkMannufacturerId(toValidate.getMannufacturerId());
    }

}
