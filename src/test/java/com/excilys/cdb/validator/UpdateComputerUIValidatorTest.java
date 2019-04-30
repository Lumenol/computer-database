package com.excilys.cdb.validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.excilys.cdb.dto.UpdateComputerDTOUi;

public class UpdateComputerUIValidatorTest {

    @Test
    public void validWithoutDateAndMannufacturerId() {
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
    public void validWithoutDateWithMannufacturerId() {
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
    public void valid() {
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
    public void unvalidBecauseIntroduced() {
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
    public void unvalidBecauseDiscontinuedAndIntroduced() {
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
    public void unvalidBecauseMannufacturerId() {
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
    public void unvalidBecauseDateIsNullValue() {
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
    public void unvalidBecauseName() {
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
    public void unvalidBecauseMannufacturerIdIsNull() {
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
    public void unvalidBecauseId() {
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
    public void unvalidBecauseIdIsNull() {
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