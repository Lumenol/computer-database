package com.excilys.cdb.validator;

import com.excilys.cdb.dto.UpdateComputerDTO;
import com.excilys.cdb.service.ComputerService;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UpdateComputerValidator implements Validator<UpdateComputerDTO> {

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
    public Result check(UpdateComputerDTO toValidate) {
        final Map<String, String> errors = new HashMap<>(createComputerValidator.check(toValidate).getErrors());
        if (!computerService.exist(toValidate.getId())) {
            errors.put("id", "l'id ne correspond à aucun ordinateur");
        }
        return new Result(errors);
    }
}
