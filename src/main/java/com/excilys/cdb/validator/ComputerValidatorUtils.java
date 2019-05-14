package com.excilys.cdb.validator;

import java.time.LocalDate;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.excilys.cdb.exception.ValidationException;
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

    void checkName(String name) {
	if (isBlank(name)) {
	    throw new ValidationException("name", "Le nom ne peux pas être vide.");
	}
    }

    void checkIntroducedIsBeforeDiscontinued(LocalDate introduced, LocalDate discontinued) {
	if (Objects.nonNull(introduced) && Objects.nonNull(discontinued) && discontinued.isBefore(introduced)) {
	    throw new ValidationException("discontinued",
		    "La date de retrait ne peux pas être avant la date d'introduction.");
	}
    }

    void checkIntroduced(LocalDate date) {
	checkDate("introduced", date);
    }

    void checkDiscontinued(LocalDate date) {
	checkDate("discontinued", date);
    }

    private void checkDate(String field, LocalDate date) {
	if (Objects.nonNull(date)) {
	    if (date.isBefore(_1970_01_01)) {
		throw new ValidationException(field, "La date ne peux pas être avant le 01-01-1970.");
	    } else if (date.isAfter(_2038_01_19)) {
		throw new ValidationException(field, "La date ne peux pas être après le 19-01-2038.");
	    }
	}
    }

    void checkMannufacturerId(Long id) {
	if (Objects.nonNull(id) && !companyService.exist(id)) {
	    throw new ValidationException("mannufacturerId", "L'id du fabriquant n'existe pas.");
	}
    }
}
