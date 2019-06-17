package com.excilys.cdb.shared.validator;

import com.excilys.cdb.shared.dto.UpdateComputerDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;

@Component
public class UpdateComputerValidator implements Validator, com.excilys.cdb.shared.validator.Validator<UpdateComputerDTO> {
    private final ComputerValidatorUtils computerValidatorUtils;
    private final ComputerExistById computerExistById;

    public UpdateComputerValidator(ComputerValidatorUtils computerValidatorUtils, ComputerExistById computerExistById) {
        super();
        this.computerValidatorUtils = computerValidatorUtils;
        this.computerExistById = computerExistById;
    }

    private void checkId(Long id, Errors errors) {
        if (Objects.isNull(id)) {
            errors.rejectValue("id", "validator.id.null");
        } else if (!computerExistById.exist(id)) {
            errors.rejectValue("id", "validator.id.notFound");
        }
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UpdateComputerDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Objects.requireNonNull(target);
        UpdateComputerDTO toValidate = (UpdateComputerDTO) target;
        computerValidatorUtils.checkName(toValidate.getName(), errors);
        computerValidatorUtils.checkIntroduced(toValidate.getIntroduced(), errors);
        computerValidatorUtils.checkDiscontinued(toValidate.getDiscontinued(), errors);
        computerValidatorUtils.checkIntroducedIsBeforeDiscontinued(toValidate.getIntroduced(),
                toValidate.getDiscontinued(), errors);
        computerValidatorUtils.checkmanufacturerId(toValidate.getManufacturerId(), errors);
        checkId(toValidate.getId(), errors);
    }
}
