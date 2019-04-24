package com.excilys.cdb.validator;

import com.excilys.cdb.dto.CreateComputerDTOUi;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreateComputerUIValidatorTest {


    @Test
    void validWithoutDateAndMannufacturerId() {
        final CreateComputerDTOUi createComputerDTOUi = new CreateComputerDTOUi();
        createComputerDTOUi.setName("Un non pas vide");
        createComputerDTOUi.setIntroduced("null");
        createComputerDTOUi.setDiscontinued("null");
        createComputerDTOUi.setMannufacturerId("null");
        final Validator.Result result = CreateComputerUIValidator.getInstance().check(createComputerDTOUi);
        assertTrue(result.isValid());
    }

    @Test
    void validWithoutDateWithMannufacturerId() {
        final CreateComputerDTOUi createComputerDTOUi = new CreateComputerDTOUi();
        createComputerDTOUi.setName("Un non pas vide");
        createComputerDTOUi.setIntroduced("null");
        createComputerDTOUi.setDiscontinued("null");
        createComputerDTOUi.setMannufacturerId("9");
        final Validator.Result result = CreateComputerUIValidator.getInstance().check(createComputerDTOUi);
        assertTrue(result.isValid());
    }

    @Test
    void valid() {
        final CreateComputerDTOUi createComputerDTOUi = new CreateComputerDTOUi();
        createComputerDTOUi.setName("Un non pas vide");
        createComputerDTOUi.setIntroduced("2017-02-12");
        createComputerDTOUi.setDiscontinued("null");
        createComputerDTOUi.setMannufacturerId("7");
        final Validator.Result result = CreateComputerUIValidator.getInstance().check(createComputerDTOUi);
        assertTrue(result.isValid());
    }

    @Test
    void unvalidBecauseIntroduced() {
        final CreateComputerDTOUi createComputerDTOUi = new CreateComputerDTOUi();
        createComputerDTOUi.setName("Un non pas vide");
        createComputerDTOUi.setIntroduced("12-02-2020");
        createComputerDTOUi.setDiscontinued("null");
        createComputerDTOUi.setMannufacturerId("7");
        final Validator.Result result = CreateComputerUIValidator.getInstance().check(createComputerDTOUi);
        assertFalse(result.isValid());
        assertNotNull(result.get("introduced"));
    }

    @Test
    void unvalidBecauseDiscontinuedAndIntroduced() {
        final CreateComputerDTOUi createComputerDTOUi = new CreateComputerDTOUi();
        createComputerDTOUi.setName("Un non pas vide");
        createComputerDTOUi.setIntroduced("nul");
        createComputerDTOUi.setDiscontinued("");
        createComputerDTOUi.setMannufacturerId("7");
        final Validator.Result result = CreateComputerUIValidator.getInstance().check(createComputerDTOUi);
        assertFalse(result.isValid());
        assertNotNull(result.get("introduced"));
        assertNotNull(result.get("discontinued"));
    }


    @Test
    void unvalidBecauseMannufacturerId() {
        final CreateComputerDTOUi createComputerDTOUi = new CreateComputerDTOUi();
        createComputerDTOUi.setName("Un non pas vide");
        createComputerDTOUi.setIntroduced("null");
        createComputerDTOUi.setDiscontinued("2017-10-25");
        createComputerDTOUi.setMannufacturerId(" 7");
        final Validator.Result result = CreateComputerUIValidator.getInstance().check(createComputerDTOUi);
        assertFalse(result.isValid());
        assertNotNull(result.get("mannufacturerId"));
    }
}