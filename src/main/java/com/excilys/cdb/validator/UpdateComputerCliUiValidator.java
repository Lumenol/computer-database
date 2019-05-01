package com.excilys.cdb.validator;

import com.excilys.cdb.dto.UpdateComputerDTOUi;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UpdateComputerCliUiValidator implements Validator<UpdateComputerDTOUi> {
    private static UpdateComputerCliUiValidator instance;
    private final CreateComputerCliUiValidator createComputerCliUiValidator = CreateComputerCliUiValidator.getInstance();

    private UpdateComputerCliUiValidator() {
    }

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

    public static UpdateComputerCliUiValidator getInstance() {
        if (Objects.isNull(instance)) {
            instance = new UpdateComputerCliUiValidator();
        }
        return instance;
    }

    @Override
    public Result check(UpdateComputerDTOUi toValidate) {
        final Map<String, String> errors = new HashMap<>(createComputerCliUiValidator.check(toValidate).getErrors());
        if (checkIdFail(toValidate.getId())) {
            errors.put("id", "l'id est mal formé");
        }
        return new Result(errors);
    }
}
