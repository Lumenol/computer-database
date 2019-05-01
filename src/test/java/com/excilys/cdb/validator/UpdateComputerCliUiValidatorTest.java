package com.excilys.cdb.validator;

import com.excilys.cdb.dto.UpdateComputerDTOUi;
import org.junit.Test;

import static org.junit.Assert.*;

public class UpdateComputerCliUiValidatorTest {

    @Test
    public void validWithoutDateAndMannufacturerId() {
        final UpdateComputerDTOUi updateComputerDTOUi = new UpdateComputerDTOUi();
        updateComputerDTOUi.setId("9");
        updateComputerDTOUi.setName("Un non pas vide");
        updateComputerDTOUi.setIntroduced("null");
        updateComputerDTOUi.setDiscontinued("null");
        updateComputerDTOUi.setMannufacturerId("null");
        final Result result = UpdateComputerCliUiValidator.getInstance().check(updateComputerDTOUi);
        assertTrue(result.isValid());
    }

    @Test
    public void validWithoutDateWithMannufacturerId() {
        final UpdateComputerDTOUi updateComputerDTOUi = new UpdateComputerDTOUi();
        updateComputerDTOUi.setId("40");
        updateComputerDTOUi.setName("Un non pas vide");
        updateComputerDTOUi.setIntroduced("null");
        updateComputerDTOUi.setDiscontinued("null");
        updateComputerDTOUi.setMannufacturerId("9");
        final Result result = UpdateComputerCliUiValidator.getInstance().check(updateComputerDTOUi);
        assertTrue(result.isValid());
    }

    @Test
    public void valid() {
        final UpdateComputerDTOUi updateComputerDTOUi = new UpdateComputerDTOUi();
        updateComputerDTOUi.setId("25");
        updateComputerDTOUi.setName("Un non pas vide");
        updateComputerDTOUi.setIntroduced("2017-02-12");
        updateComputerDTOUi.setDiscontinued("null");
        updateComputerDTOUi.setMannufacturerId("7");
        final Result result = UpdateComputerCliUiValidator.getInstance().check(updateComputerDTOUi);
        assertTrue(result.isValid());
    }

    @Test
    public void unvalidBecauseIntroduced() {
        final UpdateComputerDTOUi updateComputerDTOUi = new UpdateComputerDTOUi();
        updateComputerDTOUi.setId("9");
        updateComputerDTOUi.setName("Un non pas vide");
        updateComputerDTOUi.setIntroduced("12-02-2020");
        updateComputerDTOUi.setDiscontinued("null");
        updateComputerDTOUi.setMannufacturerId("7");
        final Result result = UpdateComputerCliUiValidator.getInstance().check(updateComputerDTOUi);
        assertFalse(result.isValid());
        assertNotNull(result.getErrors().get("introduced"));
    }

    @Test
    public void unvalidBecauseDiscontinuedAndIntroduced() {
        final UpdateComputerDTOUi updateComputerDTOUi = new UpdateComputerDTOUi();
        updateComputerDTOUi.setId("9");
        updateComputerDTOUi.setName("Un non pas vide");
        updateComputerDTOUi.setIntroduced("nul");
        updateComputerDTOUi.setDiscontinued("");
        updateComputerDTOUi.setMannufacturerId("7");
        final Result result = UpdateComputerCliUiValidator.getInstance().check(updateComputerDTOUi);
        assertFalse(result.isValid());
        assertNotNull(result.getErrors().get("introduced"));
        assertNotNull(result.getErrors().get("discontinued"));
    }

    @Test
    public void unvalidBecauseMannufacturerId() {
        final UpdateComputerDTOUi updateComputerDTOUi = new UpdateComputerDTOUi();
        updateComputerDTOUi.setId("9");
        updateComputerDTOUi.setName("Un non pas vide");
        updateComputerDTOUi.setIntroduced("null");
        updateComputerDTOUi.setDiscontinued("2017-10-25");
        updateComputerDTOUi.setMannufacturerId(" 7");
        final Result result = UpdateComputerCliUiValidator.getInstance().check(updateComputerDTOUi);
        assertFalse(result.isValid());
        assertNotNull(result.getErrors().get("mannufacturerId"));
    }

    @Test
    public void unvalidBecauseDateIsNullValue() {
        final UpdateComputerDTOUi updateComputerDTOUi = new UpdateComputerDTOUi();
        updateComputerDTOUi.setId("9");
        updateComputerDTOUi.setName("Un non pas vide");
        updateComputerDTOUi.setIntroduced(null);
        updateComputerDTOUi.setDiscontinued("2017-10-25");
        updateComputerDTOUi.setMannufacturerId("8");
        final Result result = UpdateComputerCliUiValidator.getInstance().check(updateComputerDTOUi);
        assertFalse(result.isValid());
        assertNotNull(result.getErrors().get("introduced"));
    }

    @Test
    public void unvalidBecauseName() {
        final UpdateComputerDTOUi updateComputerDTOUi = new UpdateComputerDTOUi();
        updateComputerDTOUi.setId("9");
        updateComputerDTOUi.setName("");
        updateComputerDTOUi.setIntroduced("null");
        updateComputerDTOUi.setDiscontinued("2017-10-25");
        updateComputerDTOUi.setMannufacturerId("8");
        final Result result = UpdateComputerCliUiValidator.getInstance().check(updateComputerDTOUi);
        assertFalse(result.isValid());
        assertNotNull(result.getErrors().get("name"));
    }

    @Test
    public void unvalidBecauseMannufacturerIdIsNull() {
        final UpdateComputerDTOUi updateComputerDTOUi = new UpdateComputerDTOUi();
        updateComputerDTOUi.setId("9");
        updateComputerDTOUi.setName("un nom");
        updateComputerDTOUi.setIntroduced("null");
        updateComputerDTOUi.setDiscontinued("2017-10-25");
        updateComputerDTOUi.setMannufacturerId(null);
        final Result result = UpdateComputerCliUiValidator.getInstance().check(updateComputerDTOUi);
        assertFalse(result.isValid());
        assertNotNull(result.getErrors().get("mannufacturerId"));
    }

    @Test
    public void unvalidBecauseId() {
        final UpdateComputerDTOUi updateComputerDTOUi = new UpdateComputerDTOUi();
        updateComputerDTOUi.setId("9 ");
        updateComputerDTOUi.setName("Un non pas vide");
        updateComputerDTOUi.setIntroduced("null");
        updateComputerDTOUi.setDiscontinued("2017-10-25");
        updateComputerDTOUi.setMannufacturerId("null");
        final Result result = UpdateComputerCliUiValidator.getInstance().check(updateComputerDTOUi);
        assertFalse(result.isValid());
        assertNotNull(result.getErrors().get("id"));
    }

    @Test
    public void unvalidBecauseIdIsNull() {
        final UpdateComputerDTOUi updateComputerDTOUi = new UpdateComputerDTOUi();
        updateComputerDTOUi.setId(null);
        updateComputerDTOUi.setName("Un non pas vide");
        updateComputerDTOUi.setIntroduced("null");
        updateComputerDTOUi.setDiscontinued("2017-10-25");
        updateComputerDTOUi.setMannufacturerId("null");
        final Result result = UpdateComputerCliUiValidator.getInstance().check(updateComputerDTOUi);
        assertFalse(result.isValid());
        assertNotNull(result.getErrors().get("id"));
    }
}