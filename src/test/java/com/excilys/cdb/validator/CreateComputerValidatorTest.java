package com.excilys.cdb.validator;

import static org.junit.Assert.assertEquals;
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
import org.springframework.test.context.web.WebAppConfiguration;

import com.excilys.cdb.config.AppConfig;
import com.excilys.cdb.database.UTDatabase;
import com.excilys.cdb.dto.CreateComputerDTO;
import com.excilys.cdb.exception.ValidationException;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@WebAppConfiguration
public class CreateComputerValidatorTest {

    @Autowired
    private CreateComputerValidator createComputerValidator;
    @Autowired
    private UTDatabase database;

    @Before
    public void loadEnttries() throws IOException, SQLException {
	database.reload();
    }

    @Test
    public void validWithoutDateAndMannufacturerId() {
	final CreateComputerDTO createComputerDTO = new CreateComputerDTO();
	createComputerDTO.setName("Un nom correct");
	createComputerValidator.check(createComputerDTO);
    }

    @Test
    public void validWithoutDateWithMannufacturerId() {
	final CreateComputerDTO createComputerDTO = new CreateComputerDTO();
	createComputerDTO.setName("Un nom correct");
	createComputerDTO.setMannufacturerId(5L);
	createComputerValidator.check(createComputerDTO);
    }

    @Test
    public void valid() {
	final CreateComputerDTO createComputerDTO = new CreateComputerDTO();
	createComputerDTO.setName("Un nom correct");
	createComputerDTO.setMannufacturerId(5L);
	createComputerDTO.setIntroduced(LocalDate.of(2012, 2, 4));
	createComputerDTO.setDiscontinued(LocalDate.of(2016, 10, 20));
	createComputerValidator.check(createComputerDTO);
    }

    @Test
    public void unvalidBecauseNameIsEmpty() {
	final CreateComputerDTO createComputerDTO = new CreateComputerDTO();
	createComputerDTO.setName("");
	createComputerDTO.setMannufacturerId(5L);
	createComputerDTO.setIntroduced(LocalDate.of(2012, 2, 4));
	createComputerDTO.setDiscontinued(LocalDate.of(2016, 10, 20));
	try {
	    createComputerValidator.check(createComputerDTO);
	    fail("La validation a échoué");
	} catch (ValidationException e) {
	    assertEquals("name", e.getField());
	}
    }

    @Test
    public void unvalidBecauseMannufacturerDoesNotExist() {
	final CreateComputerDTO createComputerDTO = new CreateComputerDTO();
	createComputerDTO.setName("Un nom correct");
	createComputerDTO.setMannufacturerId(5300L);
	createComputerDTO.setIntroduced(LocalDate.of(2012, 2, 4));
	createComputerDTO.setDiscontinued(LocalDate.of(2016, 10, 20));
	try {
	    createComputerValidator.check(createComputerDTO);
	    fail("La validation a échoué");
	} catch (ValidationException e) {
	    assertEquals("mannufacturerId", e.getField());
	}
    }

    @Test
    public void unvalidBecauseDiscontinuedIsBeforeIntroduced() {
	final CreateComputerDTO createComputerDTO = new CreateComputerDTO();
	createComputerDTO.setName("Un nom correct");
	createComputerDTO.setMannufacturerId(5L);
	createComputerDTO.setIntroduced(LocalDate.of(2016, 2, 4));
	createComputerDTO.setDiscontinued(LocalDate.of(2012, 10, 20));
	try {
	    createComputerValidator.check(createComputerDTO);
	    fail("La validation a échoué");
	} catch (ValidationException e) {
	    assertEquals("discontinued", e.getField());
	}
    }

}
