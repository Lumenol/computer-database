package com.excilys.cdb.validator;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.excilys.cdb.dto.CreateComputerDTO;
import com.excilys.cdb.validator.AbstractValidator.Result;

public class CreateComputerValidator extends AbstractValidator<CreateComputerDTO> {

    @Override
    protected Map<String, String> validation(CreateComputerDTO toValidate) {
	HashMap<String, String> errors = new HashMap<>();
	if (Objects.isNull(toValidate.getName()) || toValidate.getName().trim().isEmpty()) {
	    errors.put("name", "Le nom ne peux pas être nul ou vide.");
	}

	if (Objects.nonNull(toValidate.getIntroduced()) && Objects.nonNull(toValidate.getDiscontinued())
		&& toValidate.getIntroduced().isAfter(toValidate.getDiscontinued())) {
	    errors.put("discontinued", "Discontinued ne peux pas être avant Introduced.");
	}
	return errors;
    }

}
