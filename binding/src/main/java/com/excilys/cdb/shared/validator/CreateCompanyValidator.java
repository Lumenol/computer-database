package com.excilys.cdb.shared.validator;

import com.excilys.cdb.shared.dto.CreateCompanyDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.util.Objects;

@Component
public class CreateCompanyValidator implements Validator<CreateCompanyDTO> {

    @Override
    public boolean supports(Class<?> clazz) {
        return CreateCompanyDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Objects.requireNonNull(target);
        final CreateCompanyDTO toValidate = (CreateCompanyDTO) target;
        checkName(toValidate.getName(), errors);
    }


    private void checkName(String name, Errors errors) {
        if (Objects.isNull(name) || name.trim().isEmpty()) {
            errors.rejectValue("name", "validation.name.blank");
        }
    }

}
