package com.excilys.cdb.validator;

import com.excilys.cdb.database.UTDatabase;
import com.excilys.cdb.dto.CreateComputerDTO;
import com.excilys.cdb.exception.ValidationException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CreateComputerValidatorTest {

    private CreateComputerValidator createComputerValidator;

    @Before
    public void loadEnttries() throws IOException, SQLException {
        UTDatabase.getInstance().reload();
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
        createComputerDTO.setMannufacturerId("5");
        createComputerValidator.check(createComputerDTO);
    }

    @Test
    public void valid() {
        final CreateComputerDTO createComputerDTO = new CreateComputerDTO();
        createComputerDTO.setName("Un nom correct");
        createComputerDTO.setMannufacturerId("5");
        createComputerDTO.setIntroduced(LocalDate.of(2012, 2, 4).toString());
        createComputerDTO.setDiscontinued(LocalDate.of(2016, 10, 20).toString());
        createComputerValidator.check(createComputerDTO);
    }

    @Test
    public void unvalidBecauseNameIsEmpty() {
        final CreateComputerDTO createComputerDTO = new CreateComputerDTO();
        createComputerDTO.setName("");
        createComputerDTO.setMannufacturerId("5");
        createComputerDTO.setIntroduced(LocalDate.of(2012, 2, 4).toString());
        createComputerDTO.setDiscontinued(LocalDate.of(2016, 10, 20).toString());
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
        createComputerDTO.setMannufacturerId("5300");
        createComputerDTO.setIntroduced(LocalDate.of(2012, 2, 4).toString());
        createComputerDTO.setDiscontinued(LocalDate.of(2016, 10, 20).toString());
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
        createComputerDTO.setMannufacturerId("5");
        createComputerDTO.setIntroduced(LocalDate.of(2016, 2, 4).toString());
        createComputerDTO.setDiscontinued(LocalDate.of(2012, 10, 20).toString());
        try {
            createComputerValidator.check(createComputerDTO);
            fail("La validation a échoué");
        } catch (ValidationException e) {
            assertEquals("discontinued", e.getField());
        }
    }

}
