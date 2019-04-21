package com.excilys.cdb.validator;

import com.excilys.cdb.dto.UpdateComputerDTO;
import com.excilys.cdb.service.ComputerService;

import java.util.Map;
import java.util.Objects;

public class UpdateComputerValidator extends Validator<UpdateComputerDTO> {

    private static UpdateComputerValidator instance;

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
        final Map<String, String> errors = CreateComputerValidator.getInstance().validation(toValidate);
        if (!ComputerService.getInstance().exist(toValidate.getId())) {
            errors.put("id", "l'id ne correspond à aucun ordinateur");
        }
        return errors;
    }
}
