package com.excilys.cdb.validator;

import static com.excilys.cdb.validator.ValidatorUtils.isBlank;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.excilys.cdb.dto.CreateComputerDTOUi;

public class CreateComputerUIValidator extends Validator<CreateComputerDTOUi> {

    private static final String NULL = "null";
    private static CreateComputerUIValidator instance;

    private CreateComputerUIValidator() {
    }

    private static boolean checkDateFail(String date) {
	if (Objects.isNull(date)) {
	    return true;
	} else if (date.equals(NULL)) {
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
	if (Objects.isNull(id)) {
	    return true;
	} else if (id.equals(NULL)) {
	    return false;
	}
	try {
	    Long.valueOf(id);
	    return false;
	} catch (NumberFormatException e) {
	    return true;
	}
    }

    public static CreateComputerUIValidator getInstance() {
	if (Objects.isNull(instance)) {
	    instance = new CreateComputerUIValidator();
	}
	return instance;
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
