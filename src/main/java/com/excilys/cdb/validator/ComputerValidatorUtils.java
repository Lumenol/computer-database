package com.excilys.cdb.validator;

import java.time.LocalDate;
import java.util.Objects;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.excilys.cdb.service.CompanyService;

@Component
final class ComputerValidatorUtils {
    private static final LocalDate _1970_01_01 = LocalDate.of(1970, 01, 01);
    private static final LocalDate _2038_01_19 = LocalDate.of(2038, 1, 19);
    private final CompanyService companyService;

    public ComputerValidatorUtils(CompanyService companyService) {
	this.companyService = companyService;
    }

    boolean isBlank(String s) {
	return Objects.isNull(s) || s.trim().isEmpty();
    }

    void checkName(String name, Errors errors) {
	if (isBlank(name)) {
	    errors.rejectValue("name", "validation.name.blank", "Le nom ne peux pas être vide.");
	}
    }

    void checkIntroducedIsBeforeDiscontinued(LocalDate introduced, LocalDate discontinued, Errors errors) {
	if (Objects.nonNull(introduced) && Objects.nonNull(discontinued) && discontinued.isBefore(introduced)) {
	    errors.rejectValue("discontinued", "validator.discontinued.preceding",
		    "La date de retrait ne peux pas être avant la date d'introduction.");
	}
    }

    void checkIntroduced(LocalDate date, Errors errors) {
	checkDate("introduced", date, errors);
    }

    void checkDiscontinued(LocalDate date, Errors errors) {
	checkDate("discontinued", date, errors);
    }

    private void checkDate(String field, LocalDate date, Errors errors) {
	if (Objects.nonNull(date)) {
	    if (date.isBefore(_1970_01_01)) {
		errors.rejectValue(field, "validator.date.before1970", "La date ne peux pas être avant le 01-01-1970.");
	    } else if (date.isAfter(_2038_01_19)) {
		errors.rejectValue(field, "validator.date.after2038", "La date ne peux pas être après le 19-01-2038.");
	    }
	}
    }

    void checkMannufacturerId(Long id, Errors errors) {
	if (Objects.nonNull(id) && !companyService.exist(id)) {
	    errors.rejectValue("mannufacturerId", "validator.mannufacturerId", "L'id du fabriquant n'existe pas.");
	}
    }
}
