package com.excilys.cdb.shared.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.time.LocalDate;
import java.util.Objects;

@Component
final class ComputerValidatorUtils {
    private static final LocalDate _1970_01_01 = LocalDate.of(1970, 01, 01);
    private static final LocalDate _2038_01_19 = LocalDate.of(2038, 1, 19);
    private final CompanyExistById companyExistById;

    public ComputerValidatorUtils(CompanyExistById companyExistById) {
        this.companyExistById = companyExistById;
    }

    boolean isBlank(String s) {
        return Objects.isNull(s) || s.trim().isEmpty();
    }

    void checkName(String name, Errors errors) {
        if (isBlank(name)) {
            errors.rejectValue("name", "validation.name.blank");
        }
    }

    void checkIntroducedIsBeforeDiscontinued(LocalDate introduced, LocalDate discontinued, Errors errors) {
        if (Objects.nonNull(introduced) && Objects.nonNull(discontinued) && discontinued.isBefore(introduced)) {
            errors.rejectValue("discontinued", "validator.discontinued.preceding");
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
                errors.rejectValue(field, "validator.date.before1970");
            } else if (date.isAfter(_2038_01_19)) {
                errors.rejectValue(field, "validator.date.after2038");
            }
        }
    }

    void checkmanufacturerId(Long id, Errors errors) {
        if (Objects.nonNull(id) && !companyExistById.exist(id)) {
            errors.rejectValue("manufacturerId", "validator.manufacturerId.notFound");
        }
    }
}
