package com.excilys.cdb.shared.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.util.Objects;

@Component
final class CompanyValidatorUtils {

    boolean isBlank(String s) {
        return Objects.isNull(s) || s.trim().isEmpty();
    }

    void checkName(String name, Errors errors) {
        if (isBlank(name)) {
            errors.rejectValue("name", "validation.name.blank");
        }
    }
}
