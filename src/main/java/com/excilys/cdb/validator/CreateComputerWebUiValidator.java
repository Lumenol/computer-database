package com.excilys.cdb.validator;

import com.excilys.cdb.dto.CreateComputerDTOUi;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Objects;

import static com.excilys.cdb.validator.ValidatorUtils.isBlank;

public class CreateComputerWebUiValidator implements Validator<CreateComputerDTOUi> {

    private static CreateComputerWebUiValidator instance;

    private CreateComputerWebUiValidator() {
    }

    private static boolean checkDateFail(String date) {
        if (isBlank(date)) {
            return false;
        }
        try {
            LocalDate.parse(date);
            return false;
        } catch (DateTimeParseException e) {
            return true;
        }
    }

    private static boolean checkIdFail(String id) {
        if (isBlank(id)) {
            return false;
        }
        try {
            Long.valueOf(id);
            return false;
        } catch (NumberFormatException e) {
            return true;
        }
    }

    public static CreateComputerWebUiValidator getInstance() {
        if (Objects.isNull(instance)) {
            instance = new CreateComputerWebUiValidator();
        }
        return instance;
    }

    @Override
    public Result check(CreateComputerDTOUi toValidate) {
        final HashMap<String, String> errors = new HashMap<>();
        if (isBlank(toValidate.getName())) {
            errors.put("name", "Le nom ne peut pas être vide");
        }
        if (checkDateFail(toValidate.getIntroduced())) {
            errors.put("introduced", "introduced est mal formée");
        }
        if (checkDateFail(toValidate.getDiscontinued())) {
            errors.put("discontinued", "discontinued est mal formée");
        }
        if (checkIdFail(toValidate.getMannufacturerId())) {
            errors.put("mannufacturerId", "l'id du fabriquant est mal formé");
        }
        return new Result(errors);
    }
}
