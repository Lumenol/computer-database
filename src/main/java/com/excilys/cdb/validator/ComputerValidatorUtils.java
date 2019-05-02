package com.excilys.cdb.validator;

import com.excilys.cdb.exception.ValidationException;
import com.excilys.cdb.service.CompanyService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Objects;

import static com.excilys.cdb.validator.ValidatorUtils.isBlank;

class ComputerValidatorUtils {
    private ComputerValidatorUtils() {
    }

    static void checkName(String name) {
        if (isBlank(name)) {
            throw new ValidationException("name", "Le nom ne peux pas être vide.");
        }
    }

    static void checkIntroducedIsBeforeDiscontinued(LocalDate introduced, LocalDate discontinued) {
        if (Objects.nonNull(introduced) && Objects.nonNull(discontinued) && discontinued.isBefore(introduced)) {
            throw new ValidationException("discontinued", "La date de retrait ne peux pas être avant la date d'introduction.");
        }
    }

    static LocalDate checkIntroduced(String date) {
        return checkDate("introduced", date);
    }

    static LocalDate checkDiscontinued(String date) {
        return checkDate("discontinued", date);
    }

    private static LocalDate checkDate(String field, String date) {
        if (Objects.isNull(date) || date.isEmpty()) {
            return null;
        }
        try {
            return LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            throw new ValidationException(field, "La date est mal écrit.");
        }
    }

    static void checkMannufacturerId(String id) {
        if (Objects.isNull(id) || id.isEmpty()) {
            return;
        }
        try {
            long i = Long.parseLong(id);
            if (!CompanyService.getInstance().exist(i)) {
                throw new ValidationException("mannufacturerId", "L'id du fabriquant n'existe pas.");
            }
        } catch (DateTimeParseException e) {
            throw new ValidationException("mannufacturerId", "L'id du fabriquant est mal écrit.");
        }
    }
}
