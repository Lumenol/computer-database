package com.excilys.cdb.validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.excilys.cdb.dto.CreateComputerDTOUi;

public class CreateComputerUIValidatorTest {

    @Test
    public void validWithoutDateAndMannufacturerId() {
	final CreateComputerDTOUi createComputerDTOUi = new CreateComputerDTOUi();
	createComputerDTOUi.setName("Un non pas vide");
	createComputerDTOUi.setIntroduced("null");
	createComputerDTOUi.setDiscontinued("null");
	createComputerDTOUi.setMannufacturerId("null");
	final Validator.Result result = CreateComputerUIValidator.getInstance().check(createComputerDTOUi);
	assertTrue(result.isValid());
    }

    @Test
    public void validWithoutDateWithMannufacturerId() {
	final CreateComputerDTOUi createComputerDTOUi = new CreateComputerDTOUi();
	createComputerDTOUi.setName("Un non pas vide");
	createComputerDTOUi.setIntroduced("null");
	createComputerDTOUi.setDiscontinued("null");
	createComputerDTOUi.setMannufacturerId("9");
	final Validator.Result result = CreateComputerUIValidator.getInstance().check(createComputerDTOUi);
	assertTrue(result.isValid());
    }

    @Test
    public void valid() {
	final CreateComputerDTOUi createComputerDTOUi = new CreateComputerDTOUi();
	createComputerDTOUi.setName("Un non pas vide");
	createComputerDTOUi.setIntroduced("2017-02-12");
	createComputerDTOUi.setDiscontinued("null");
	createComputerDTOUi.setMannufacturerId("7");
	final Validator.Result result = CreateComputerUIValidator.getInstance().check(createComputerDTOUi);
	assertTrue(result.isValid());
    }

    @Test
    public void unvalidBecauseIntroduced() {
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
    public void unvalidBecauseDiscontinuedAndIntroduced() {
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
    public void unvalidBecauseMannufacturerId() {
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