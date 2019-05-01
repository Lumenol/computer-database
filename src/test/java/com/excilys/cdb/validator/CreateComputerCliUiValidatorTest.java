package com.excilys.cdb.validator;

import com.excilys.cdb.dto.CreateComputerDTOUi;
import org.junit.Test;

import static org.junit.Assert.*;

public class CreateComputerCliUiValidatorTest {

    @Test
    public void validWithoutDateAndMannufacturerId() {
        final CreateComputerDTOUi createComputerDTOUi = new CreateComputerDTOUi();
        createComputerDTOUi.setName("Un non pas vide");
        createComputerDTOUi.setIntroduced("null");
        createComputerDTOUi.setDiscontinued("null");
        createComputerDTOUi.setMannufacturerId("null");
        final Result result = CreateComputerCliUiValidator.getInstance().check(createComputerDTOUi);
        assertTrue(result.isValid());
    }

    @Test
    public void validWithoutDateWithMannufacturerId() {
        final CreateComputerDTOUi createComputerDTOUi = new CreateComputerDTOUi();
        createComputerDTOUi.setName("Un non pas vide");
        createComputerDTOUi.setIntroduced("null");
        createComputerDTOUi.setDiscontinued("null");
        createComputerDTOUi.setMannufacturerId("9");
        final Result result = CreateComputerCliUiValidator.getInstance().check(createComputerDTOUi);
        assertTrue(result.isValid());
    }

    @Test
    public void valid() {
        final CreateComputerDTOUi createComputerDTOUi = new CreateComputerDTOUi();
        createComputerDTOUi.setName("Un non pas vide");
        createComputerDTOUi.setIntroduced("2017-02-12");
        createComputerDTOUi.setDiscontinued("null");
        createComputerDTOUi.setMannufacturerId("7");
        final Result result = CreateComputerCliUiValidator.getInstance().check(createComputerDTOUi);
        assertTrue(result.isValid());
    }

    @Test
    public void unvalidBecauseIntroduced() {
        final CreateComputerDTOUi createComputerDTOUi = new CreateComputerDTOUi();
        createComputerDTOUi.setName("Un non pas vide");
        createComputerDTOUi.setIntroduced("12-02-2020");
        createComputerDTOUi.setDiscontinued("null");
        createComputerDTOUi.setMannufacturerId("7");
        final Result result = CreateComputerCliUiValidator.getInstance().check(createComputerDTOUi);
        assertFalse(result.isValid());
        assertNotNull(result.getErrors().get("introduced"));
    }

    @Test
    public void unvalidBecauseDiscontinuedAndIntroduced() {
        final CreateComputerDTOUi createComputerDTOUi = new CreateComputerDTOUi();
        createComputerDTOUi.setName("Un non pas vide");
        createComputerDTOUi.setIntroduced("nul");
        createComputerDTOUi.setDiscontinued("");
        createComputerDTOUi.setMannufacturerId("7");
        final Result result = CreateComputerCliUiValidator.getInstance().check(createComputerDTOUi);
        assertFalse(result.isValid());
        assertNotNull(result.getErrors().get("introduced"));
        assertNotNull(result.getErrors().get("discontinued"));
    }

    @Test
    public void unvalidBecauseMannufacturerId() {
        final CreateComputerDTOUi createComputerDTOUi = new CreateComputerDTOUi();
        createComputerDTOUi.setName("Un non pas vide");
        createComputerDTOUi.setIntroduced("null");
        createComputerDTOUi.setDiscontinued("2017-10-25");
        createComputerDTOUi.setMannufacturerId(" 7");
        final Result result = CreateComputerCliUiValidator.getInstance().check(createComputerDTOUi);
        assertFalse(result.isValid());
        assertNotNull(result.getErrors().get("mannufacturerId"));
    }
}