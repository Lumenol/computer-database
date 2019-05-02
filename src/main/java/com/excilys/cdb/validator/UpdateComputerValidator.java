package com.excilys.cdb.validator;

import com.excilys.cdb.dto.UpdateComputerDTO;
import com.excilys.cdb.exception.ValidationException;
import com.excilys.cdb.service.ComputerService;

import java.time.LocalDate;
import java.util.Objects;

import static com.excilys.cdb.validator.ComputerValidatorUtils.*;

public class UpdateComputerValidator implements Validator<UpdateComputerDTO> {

    private static UpdateComputerValidator instance;

    private UpdateComputerValidator() {
    }

    public static UpdateComputerValidator getInstance() {
        if (Objects.isNull(instance)) {
            instance = new UpdateComputerValidator();
        }
        return instance;
    }

    private void checkId(String id) {
        try {
            final long i = Long.parseLong(id);
            if (!ComputerService.getInstance().exist(i)) {
                throw new ValidationException("id", "L'id n'exist pas.");
            }
        } catch (NumberFormatException e) {
            throw new ValidationException("id", "L'id est mal écrit.");
        }
    }

    @Override
    public void check(UpdateComputerDTO toValidate) {
        checkName(toValidate.getName());
        final LocalDate introduced = checkIntroduced(toValidate.getIntroduced());
        final LocalDate discontinued = checkDiscontinued(toValidate.getDiscontinued());
        checkIntroducedIsBeforeDiscontinued(introduced, discontinued);
        checkMannufacturerId(toValidate.getMannufacturerId());
        checkId(toValidate.getId());
    }
}
