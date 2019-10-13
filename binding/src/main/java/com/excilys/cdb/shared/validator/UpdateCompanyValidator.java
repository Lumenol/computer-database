package com.excilys.cdb.shared.validator;

import com.excilys.cdb.shared.dto.UpdateCompanyDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.util.Objects;

@Component
public class UpdateCompanyValidator implements org.springframework.validation.Validator {

    private final CompanyValidatorUtils companyValidatorUtils;
    private final CompanyExistById companyExistById;


    public UpdateCompanyValidator(CompanyValidatorUtils companyValidatorUtils, CompanyExistById companyExistById) {
        this.companyValidatorUtils = companyValidatorUtils;
        this.companyExistById = companyExistById;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return UpdateCompanyDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Objects.requireNonNull(target);
        final UpdateCompanyDTO toValidate = (UpdateCompanyDTO) target;
        companyValidatorUtils.checkName(toValidate.getName(), errors);
        checkId(toValidate.getId(), errors);
    }

    private void checkId(Long id, Errors errors) {
        if (Objects.isNull(id)) {
            errors.rejectValue("id", "validator.id.null");
        } else if (!companyExistById.exist(id)) {
            errors.rejectValue("id", "validator.id.notFound");
        }
    }

}
