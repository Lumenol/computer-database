package com.excilys.cdb.validator;

import com.excilys.cdb.dto.UpdateComputerDTOUi;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UpdateComputerUIValidatorTest {


    @Test
    void validWithoutDateAndMannufacturerId() {
        final UpdateComputerDTOUi updateComputerDTOUi = new UpdateComputerDTOUi();
        updateComputerDTOUi.setId("9");
        updateComputerDTOUi.setName("Un non pas vide");
        updateComputerDTOUi.setIntroduced("null");
        updateComputerDTOUi.setDiscontinued("null");
        updateComputerDTOUi.setMannufacturerId("null");
        final Validator.Result result = UpdateComputerUIValidator.getInstance().check(updateComputerDTOUi);
        assertTrue(result.isValid());
    }

    @Test
    void validWithoutDateWithMannufacturerId() {
        final UpdateComputerDTOUi updateComputerDTOUi = new UpdateComputerDTOUi();
        updateComputerDTOUi.setId("40");
        updateComputerDTOUi.setName("Un non pas vide");
        updateComputerDTOUi.setIntroduced("null");
        updateComputerDTOUi.setDiscontinued("null");
        updateComputerDTOUi.setMannufacturerId("9");
        final Validator.Result result = UpdateComputerUIValidator.getInstance().check(updateComputerDTOUi);
        assertTrue(result.isValid());
    }

    @Test
    void valid() {
        final UpdateComputerDTOUi updateComputerDTOUi = new UpdateComputerDTOUi();
        updateComputerDTOUi.setId("25");
        updateComputerDTOUi.setName("Un non pas vide");
        updateComputerDTOUi.setIntroduced("2017-02-12");
        updateComputerDTOUi.setDiscontinued("null");
        updateComputerDTOUi.setMannufacturerId("7");
        final Validator.Result result = UpdateComputerUIValidator.getInstance().check(updateComputerDTOUi);
        assertTrue(result.isValid());
    }

    @Test
    void unvalidBecauseIntroduced() {
        final UpdateComputerDTOUi updateComputerDTOUi = new UpdateComputerDTOUi();
        updateComputerDTOUi.setId("9");
        updateComputerDTOUi.setName("Un non pas vide");
        updateComputerDTOUi.setIntroduced("12-02-2020");
        updateComputerDTOUi.setDiscontinued("null");
        updateComputerDTOUi.setMannufacturerId("7");
        final Validator.Result result = UpdateComputerUIValidator.getInstance().check(updateComputerDTOUi);
        assertFalse(result.isValid());
        assertNotNull(result.get("introduced"));
    }

    @Test
    void unvalidBecauseDiscontinuedAndIntroduced() {
        final UpdateComputerDTOUi updateComputerDTOUi = new UpdateComputerDTOUi();
        updateComputerDTOUi.setId("9");
        updateComputerDTOUi.setName("Un non pas vide");
        updateComputerDTOUi.setIntroduced("nul");
        updateComputerDTOUi.setDiscontinued("");
        updateComputerDTOUi.setMannufacturerId("7");
        final Validator.Result result = UpdateComputerUIValidator.getInstance().check(updateComputerDTOUi);
        assertFalse(result.isValid());
        assertNotNull(result.get("introduced"));
        assertNotNull(result.get("discontinued"));
    }


    @Test
    void unvalidBecauseMannufacturerId() {
        final UpdateComputerDTOUi updateComputerDTOUi = new UpdateComputerDTOUi();
        updateComputerDTOUi.setId("9");
        updateComputerDTOUi.setName("Un non pas vide");
        updateComputerDTOUi.setIntroduced("null");
        updateComputerDTOUi.setDiscontinued("2017-10-25");
        updateComputerDTOUi.setMannufacturerId(" 7");
        final Validator.Result result = UpdateComputerUIValidator.getInstance().check(updateComputerDTOUi);
        assertFalse(result.isValid());
        assertNotNull(result.get("mannufacturerId"));
    }

    @Test
    void unvalidBecauseDateIsNullValue() {
        final UpdateComputerDTOUi updateComputerDTOUi = new UpdateComputerDTOUi();
        updateComputerDTOUi.setId("9");
        updateComputerDTOUi.setName("Un non pas vide");
        updateComputerDTOUi.setIntroduced(null);
        updateComputerDTOUi.setDiscontinued("2017-10-25");
        updateComputerDTOUi.setMannufacturerId("8");
        final Validator.Result result = UpdateComputerUIValidator.getInstance().check(updateComputerDTOUi);
        assertFalse(result.isValid());
        assertNotNull(result.get("introduced"));
    }

    @Test
    void unvalidBecauseName() {
        final UpdateComputerDTOUi updateComputerDTOUi = new UpdateComputerDTOUi();
        updateComputerDTOUi.setId("9");
        updateComputerDTOUi.setName("");
        updateComputerDTOUi.setIntroduced("null");
        updateComputerDTOUi.setDiscontinued("2017-10-25");
        updateComputerDTOUi.setMannufacturerId("8");
        final Validator.Result result = UpdateComputerUIValidator.getInstance().check(updateComputerDTOUi);
        assertFalse(result.isValid());
        assertNotNull(result.get("name"));
    }

    @Test
    void unvalidBecauseMannufacturerIdIsNull() {
        final UpdateComputerDTOUi updateComputerDTOUi = new UpdateComputerDTOUi();
        updateComputerDTOUi.setId("9");
        updateComputerDTOUi.setName("un nom");
        updateComputerDTOUi.setIntroduced("null");
        updateComputerDTOUi.setDiscontinued("2017-10-25");
        updateComputerDTOUi.setMannufacturerId(null);
        final Validator.Result result = UpdateComputerUIValidator.getInstance().check(updateComputerDTOUi);
        assertFalse(result.isValid());
        assertNotNull(result.get("mannufacturerId"));
    }

    @Test
    void unvalidBecauseId() {
        final UpdateComputerDTOUi updateComputerDTOUi = new UpdateComputerDTOUi();
        updateComputerDTOUi.setId("9 ");
        updateComputerDTOUi.setName("Un non pas vide");
        updateComputerDTOUi.setIntroduced("null");
        updateComputerDTOUi.setDiscontinued("2017-10-25");
        updateComputerDTOUi.setMannufacturerId("null");
        final Validator.Result result = UpdateComputerUIValidator.getInstance().check(updateComputerDTOUi);
        assertFalse(result.isValid());
        assertNotNull(result.get("id"));
    }

    @Test
    void unvalidBecauseIdIsNull() {
        final UpdateComputerDTOUi updateComputerDTOUi = new UpdateComputerDTOUi();
        updateComputerDTOUi.setId(null);
        updateComputerDTOUi.setName("Un non pas vide");
        updateComputerDTOUi.setIntroduced("null");
        updateComputerDTOUi.setDiscontinued("2017-10-25");
        updateComputerDTOUi.setMannufacturerId("null");
        final Validator.Result result = UpdateComputerUIValidator.getInstance().check(updateComputerDTOUi);
        assertFalse(result.isValid());
        assertNotNull(result.get("id"));
    }
}