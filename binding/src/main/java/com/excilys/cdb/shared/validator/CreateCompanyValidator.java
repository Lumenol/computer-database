package com.excilys.cdb.shared.validator;

import com.excilys.cdb.shared.dto.CreateCompanyDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.util.Objects;

@Component
public class CreateCompanyValidator implements Validator<CreateCompanyDTO> {

    private final CompanyValidatorUtils companyValidatorUtils;

    public CreateCompanyValidator(CompanyValidatorUtils companyValidatorUtils) {
        this.companyValidatorUtils = companyValidatorUtils;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return CreateCompanyDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Objects.requireNonNull(target);
        final CreateCompanyDTO toValidate = (CreateCompanyDTO) target;
        companyValidatorUtils.checkName(toValidate.getName(), errors);
    }

}
