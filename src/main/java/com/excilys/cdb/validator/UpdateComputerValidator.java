package com.excilys.cdb.validator;

import java.util.Map;
import java.util.Objects;

import com.excilys.cdb.dto.UpdateComputerDTO;
import com.excilys.cdb.service.ComputerService;

public class UpdateComputerValidator extends Validator<UpdateComputerDTO> {

    private static UpdateComputerValidator instance;
    private final ComputerService computerService = ComputerService.getInstance();
    private final CreateComputerValidator createComputerValidator = CreateComputerValidator.getInstance();

    private UpdateComputerValidator() {
    }

    public static UpdateComputerValidator getInstance() {
	if (Objects.isNull(instance)) {
	    instance = new UpdateComputerValidator();
	}
	return instance;
    }

    @Override
    protected Map<String, String> validation(UpdateComputerDTO toValidate) {
	final Map<String, String> errors = createComputerValidator.validation(toValidate);
	if (!computerService.exist(toValidate.getId())) {
	    errors.put("id", "l'id ne correspond à aucun ordinateur");
	}
	return errors;
    }
}
