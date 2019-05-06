package com.excilys.cdb.validator;

import static com.excilys.cdb.validator.ValidatorUtils.isBlank;

import java.util.Objects;

import com.excilys.cdb.dto.CreateCompanyDTO;
import com.excilys.cdb.exception.ValidationException;

public class CreateCompanyDTOValidator implements Validator<CreateCompanyDTO> {

    private static CreateCompanyDTOValidator instance;

    private CreateCompanyDTOValidator() {
    }

    public static synchronized CreateCompanyDTOValidator getInstance() {
	if (Objects.isNull(instance)) {
	    instance = new CreateCompanyDTOValidator();
	}
	return instance;
    }

    private void checkName(String name) {
	if (isBlank(name)) {
	    throw new ValidationException("name", "Le nom ne peux pas être vide.");
	}
    }

    @Override
    public void check(CreateCompanyDTO toValidate) {
	Objects.requireNonNull(toValidate);
	checkName(toValidate.getName());
    }

}
