package com.excilys.cdb.validator;

import com.excilys.cdb.dto.CreateComputerDTO;

import java.time.LocalDate;
import java.util.Objects;

import static com.excilys.cdb.validator.ComputerValidatorUtils.*;

public class CreateComputerValidator implements Validator<CreateComputerDTO> {

    private static CreateComputerValidator instance;

    private CreateComputerValidator() {
    }

    public static synchronized CreateComputerValidator getInstance() {
        if (Objects.isNull(instance)) {
            instance = new CreateComputerValidator();
        }
        return instance;
    }

    @Override
    public void check(CreateComputerDTO toValidate) {
        Objects.requireNonNull(toValidate);
        checkName(toValidate.getName());
        final LocalDate introduced = checkIntroduced(toValidate.getIntroduced());
        final LocalDate discontinued = checkDiscontinued(toValidate.getDiscontinued());
        checkIntroducedIsBeforeDiscontinued(introduced, discontinued);
        checkMannufacturerId(toValidate.getMannufacturerId());
    }

}
