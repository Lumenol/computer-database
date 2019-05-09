package com.excilys.cdb.validator;

import com.excilys.cdb.database.UTDatabase;
import com.excilys.cdb.dto.UpdateComputerDTO;
import com.excilys.cdb.exception.ValidationException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class UpdateComputerValidatorTest {
    @Before
    public void loadEnttries() throws IOException, SQLException {
        UTDatabase.getInstance().reload();
    }

    @Test
    public void validWithoutDateAndMannufacturerId() {
        final UpdateComputerDTO updateComputerDTO = new UpdateComputerDTO();
        updateComputerDTO.setId("5");
        updateComputerDTO.setName("Un nom correct");
        UpdateComputerValidator.getInstance().check(updateComputerDTO);
    }

    @Test
    public void validWithoutDateWithMannufacturerId() {
        final UpdateComputerDTO updateComputerDTO = new UpdateComputerDTO();
        updateComputerDTO.setId("3");
        updateComputerDTO.setName("Un nom correct");
        updateComputerDTO.setMannufacturerId("5");
        UpdateComputerValidator.getInstance().check(updateComputerDTO);
    }

    @Test
    public void valid() {
        final UpdateComputerDTO updateComputerDTO = new UpdateComputerDTO();
        updateComputerDTO.setId("9");
        updateComputerDTO.setName("Un nom correct");
        updateComputerDTO.setMannufacturerId("5");
        updateComputerDTO.setIntroduced(LocalDate.of(2012, 2, 4).toString());
        updateComputerDTO.setDiscontinued(LocalDate.of(2016, 10, 20).toString());
        UpdateComputerValidator.getInstance().check(updateComputerDTO);
    }

    @Test
    public void unvalidBecauseNameIsEmpty() {
        final UpdateComputerDTO updateComputerDTO = new UpdateComputerDTO();
        updateComputerDTO.setId("3");
        updateComputerDTO.setName("");
        updateComputerDTO.setMannufacturerId("5");
        updateComputerDTO.setIntroduced(LocalDate.of(2012, 2, 4).toString());
        updateComputerDTO.setDiscontinued(LocalDate.of(2016, 10, 20).toString());
        try {
            UpdateComputerValidator.getInstance().check(updateComputerDTO);
        } catch (ValidationException e) {
            assertEquals("name", e.getField());
            return;
        }
        fail("La validation a échoué");

    }

    @Test
    public void unvalidBecauseMannufacturerDoesNotExist() {
        final UpdateComputerDTO updateComputerDTO = new UpdateComputerDTO();
        updateComputerDTO.setId("3");
        updateComputerDTO.setName("Un nom correct");
        updateComputerDTO.setMannufacturerId("5300");
        updateComputerDTO.setIntroduced(LocalDate.of(2012, 2, 4).toString());
        updateComputerDTO.setDiscontinued(LocalDate.of(2016, 10, 20).toString());
        try {
            UpdateComputerValidator.getInstance().check(updateComputerDTO);
            fail("La validation a échoué");
        } catch (ValidationException e) {
            assertEquals("mannufacturerId", e.getField());
        }
    }

    @Test
    public void unvalidBecauseDiscontinuedIsBeforeIntroduced() {
        final UpdateComputerDTO updateComputerDTO = new UpdateComputerDTO();
        updateComputerDTO.setId("3");
        updateComputerDTO.setName("Un nom correct");
        updateComputerDTO.setMannufacturerId("5");
        updateComputerDTO.setIntroduced(LocalDate.of(2016, 2, 4).toString());
        updateComputerDTO.setDiscontinued(LocalDate.of(2012, 10, 20).toString());
        try {
            UpdateComputerValidator.getInstance().check(updateComputerDTO);
            fail("La validation a échoué");
        } catch (ValidationException e) {
            assertEquals("discontinued", e.getField());
        }
    }

    @Test
    public void unvalidBecauseComputerDoesNotExist() {
        final UpdateComputerDTO updateComputerDTO = new UpdateComputerDTO();
        updateComputerDTO.setId("984");
        updateComputerDTO.setName("Un nom correct");
        updateComputerDTO.setMannufacturerId("5");
        updateComputerDTO.setIntroduced(LocalDate.of(2012, 2, 4).toString());
        updateComputerDTO.setDiscontinued(LocalDate.of(2016, 10, 20).toString());
        try {
            UpdateComputerValidator.getInstance().check(updateComputerDTO);
            fail("La validation a échoué");
        } catch (ValidationException e) {
            assertEquals("id", e.getField());
        }
    }

}
