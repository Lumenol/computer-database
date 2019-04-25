package com.excilys.cdb.validator;

import java.util.Map;
import java.util.Objects;

import com.excilys.cdb.dto.UpdateComputerDTOUi;

public class UpdateComputerUIValidator extends Validator<UpdateComputerDTOUi> {
    private static UpdateComputerUIValidator instance;
    private static boolean checkIdFail(String id) {
	if (Objects.isNull(id)) {
	    return true;
	}
	try {
	    Long.valueOf(id);
	    return false;
	} catch (NumberFormatException e) {
	    return true;
	}
    }

    public static UpdateComputerUIValidator getInstance() {
	if (Objects.isNull(instance)) {
	    instance = new UpdateComputerUIValidator();
	}
	return instance;
    }

    private final CreateComputerUIValidator createComputerUIValidator = CreateComputerUIValidator.getInstance();

    private UpdateComputerUIValidator() {
    }

    @Override
    protected Map<String, String> validation(UpdateComputerDTOUi toValidate) {
	final Map<String, String> errors = createComputerUIValidator.validation(toValidate);
	if (checkIdFail(toValidate.getId())) {
	    errors.put("id", "l'id est mal formé");
	}
	return errors;
    }
}
