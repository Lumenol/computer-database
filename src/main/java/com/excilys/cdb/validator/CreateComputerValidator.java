package com.excilys.cdb.validator;

import com.excilys.cdb.dto.CreateComputerDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Objects;

@Component
public class CreateComputerValidator implements Validator<CreateComputerDTO> {

    private final ComputerValidatorUtils computerValidatorUtils;

    public CreateComputerValidator(ComputerValidatorUtils computerValidatorUtils) {
        this.computerValidatorUtils = computerValidatorUtils;
    }

    @Override
    public void check(CreateComputerDTO toValidate) {
        Objects.requireNonNull(toValidate);
        computerValidatorUtils.checkName(toValidate.getName());
        final LocalDate introduced = computerValidatorUtils.checkIntroduced(toValidate.getIntroduced());
        final LocalDate discontinued = computerValidatorUtils.checkDiscontinued(toValidate.getDiscontinued());
        computerValidatorUtils.checkIntroducedIsBeforeDiscontinued(introduced, discontinued);
        computerValidatorUtils.checkMannufacturerId(toValidate.getMannufacturerId());
    }

}
