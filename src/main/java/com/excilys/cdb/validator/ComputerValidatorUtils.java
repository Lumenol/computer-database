package com.excilys.cdb.validator;

import com.excilys.cdb.exception.ValidationException;
import com.excilys.cdb.service.CompanyService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Objects;

@Component
final class ComputerValidatorUtils {
    private static final LocalDate _1970_01_01 = LocalDate.of(1970, 01, 01);
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

    LocalDate checkIntroduced(String date) {
        return checkDate("introduced", date);
    }

    LocalDate checkDiscontinued(String date) {
        return checkDate("discontinued", date);
    }

    private LocalDate checkDate(String field, String date) {
        if (Objects.isNull(date) || date.isEmpty()) {
            return null;
        }
        try {
            final LocalDate localDate = LocalDate.parse(date);
            if (localDate.isBefore(_1970_01_01)) {
                throw new ValidationException(field, "La date ne peux pas être avant le 01-01-1970.");
            }
            return localDate;
        } catch (DateTimeParseException e) {
            throw new ValidationException(field, "La date est mal écrit.");
        }
    }

    public void checkMannufacturerId(String id) {
        if (Objects.isNull(id) || id.isEmpty()) {
            return;
        }
        try {
            long i = Long.parseLong(id);
            if (!companyService.exist(i)) {
                throw new ValidationException("mannufacturerId", "L'id du fabriquant n'existe pas.");
            }
        } catch (DateTimeParseException e) {
            throw new ValidationException("mannufacturerId", "L'id du fabriquant est mal écrit.");
        }
    }
}
