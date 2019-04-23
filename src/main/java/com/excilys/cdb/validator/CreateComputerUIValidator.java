package com.excilys.cdb.validator;

import com.excilys.cdb.dto.CreateComputerDTOUi;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.excilys.cdb.validator.ValidatorUtils.isBlank;

public class CreateComputerUIValidator extends Validator<CreateComputerDTOUi> {

    private static final String NULL = "null";
    private static CreateComputerUIValidator instance;

    private CreateComputerUIValidator() {
    }

    public static CreateComputerUIValidator getInstance() {
        if (Objects.isNull(instance)) {
            instance = new CreateComputerUIValidator();
        }
        return instance;
    }

    private static boolean checkDateFail(String date) {
        if (date.equals(NULL)) {
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
        if (id.equals(NULL)) {
            return false;
        }
        try {
            Long.valueOf(id);
            return false;
        } catch (NumberFormatException e) {
            return true;
        }
    }

    @Override
    protected Map<String, String> validation(CreateComputerDTOUi toValidate) {
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
        return errors;
    }
}