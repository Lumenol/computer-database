package com.excilys.cdb.validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.BindException;

import com.excilys.cdb.config.TestConfig;
import com.excilys.cdb.database.UTDatabase;
import com.excilys.cdb.dto.UpdateComputerDTO;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class UpdateComputerValidatorTest {

    @Autowired
    private UpdateComputerValidator updateComputerValidator;
    @Autowired
    private UTDatabase database;

    @Before
    public void loadEnttries() throws IOException, SQLException {
	database.reload();
    }

    @Test
    public void validWithoutDateAndMannufacturerId() {
	final UpdateComputerDTO updateComputerDTO = new UpdateComputerDTO();
	updateComputerDTO.setId(5L);
	updateComputerDTO.setName("Un nom correct");
	final BindException errors = new BindException(updateComputerDTO, "dto");
	updateComputerValidator.validate(updateComputerDTO, errors);
	assertFalse(errors.hasErrors());
    }

    @Test
    public void validWithoutDateWithMannufacturerId() {
	final UpdateComputerDTO updateComputerDTO = new UpdateComputerDTO();
	updateComputerDTO.setId(3L);
	updateComputerDTO.setName("Un nom correct");
	updateComputerDTO.setMannufacturerId(5L);
	final BindException errors = new BindException(updateComputerDTO, "dto");
	updateComputerValidator.validate(updateComputerDTO, errors);
	assertFalse(errors.hasErrors());
    }

    @Test
    public void valid() {
	final UpdateComputerDTO updateComputerDTO = new UpdateComputerDTO();
	updateComputerDTO.setId(9L);
	updateComputerDTO.setName("Un nom correct");
	updateComputerDTO.setMannufacturerId(5L);
	updateComputerDTO.setIntroduced(LocalDate.of(2012, 2, 4));
	updateComputerDTO.setDiscontinued(LocalDate.of(2016, 10, 20));
	final BindException errors = new BindException(updateComputerDTO, "dto");
	updateComputerValidator.validate(updateComputerDTO, errors);
	assertFalse(errors.hasErrors());
    }

    @Test
    public void unvalidBecauseNameIsEmpty() {
	final UpdateComputerDTO updateComputerDTO = new UpdateComputerDTO();
	updateComputerDTO.setId(3L);
	updateComputerDTO.setName("");
	updateComputerDTO.setMannufacturerId(5L);
	updateComputerDTO.setIntroduced(LocalDate.of(2012, 2, 4));
	updateComputerDTO.setDiscontinued(LocalDate.of(2016, 10, 20));
	final BindException errors = new BindException(updateComputerDTO, "dto");
	updateComputerValidator.validate(updateComputerDTO, errors);
	if (!errors.hasFieldErrors("name")) {
	    fail("La validation a échoué");
	}
    }

    @Test
    public void unvalidBecauseMannufacturerDoesNotExist() {
	final UpdateComputerDTO updateComputerDTO = new UpdateComputerDTO();
	updateComputerDTO.setId(3L);
	updateComputerDTO.setName("Un nom correct");
	updateComputerDTO.setMannufacturerId(5300L);
	updateComputerDTO.setIntroduced(LocalDate.of(2012, 2, 4));
	updateComputerDTO.setDiscontinued(LocalDate.of(2016, 10, 20));
	final BindException errors = new BindException(updateComputerDTO, "dto");
	updateComputerValidator.validate(updateComputerDTO, errors);
	if (!errors.hasFieldErrors("mannufacturerId")) {
	    fail("La validation a échoué");
	}
    }

    @Test
    public void unvalidBecauseDiscontinuedIsBeforeIntroduced() {
	final UpdateComputerDTO updateComputerDTO = new UpdateComputerDTO();
	updateComputerDTO.setId(3L);
	updateComputerDTO.setName("Un nom correct");
	updateComputerDTO.setMannufacturerId(5L);
	updateComputerDTO.setIntroduced(LocalDate.of(2016, 2, 4));
	updateComputerDTO.setDiscontinued(LocalDate.of(2012, 10, 20));
	final BindException errors = new BindException(updateComputerDTO, "dto");
	updateComputerValidator.validate(updateComputerDTO, errors);
	if (!errors.hasFieldErrors("discontinued")) {
	    fail("La validation a échoué");
	}
    }

    @Test
    public void unvalidBecauseComputerIdNotNumber() {
	final UpdateComputerDTO updateComputerDTO = new UpdateComputerDTO();
	updateComputerDTO.setId(null);
	updateComputerDTO.setName("Un nom correct");
	updateComputerDTO.setMannufacturerId(5L);
	updateComputerDTO.setIntroduced(LocalDate.of(2012, 2, 4));
	updateComputerDTO.setDiscontinued(LocalDate.of(2016, 10, 20));
	final BindException errors = new BindException(updateComputerDTO, "dto");
	updateComputerValidator.validate(updateComputerDTO, errors);
	if (!errors.hasFieldErrors("id")) {
	    fail("La validation a échoué");
	}
    }

    @Test
    public void unvalidBecauseIntroducedIsBefore1970() {
	final UpdateComputerDTO updateComputerDTO = new UpdateComputerDTO();
	updateComputerDTO.setId(2L);
	updateComputerDTO.setName("Un nom correct");
	updateComputerDTO.setMannufacturerId(5L);
	updateComputerDTO.setIntroduced(LocalDate.of(1969, 10, 20));
	updateComputerDTO.setDiscontinued(LocalDate.of(2016, 10, 20));
	final BindException errors = new BindException(updateComputerDTO, "dto");
	updateComputerValidator.validate(updateComputerDTO, errors);
	if (!errors.hasFieldErrors("introduced")) {
	    fail("La validation a échoué");
	}
    }

}
