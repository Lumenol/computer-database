package com.excilys.cdb.validator;

import com.excilys.cdb.database.UTDatabase;
import com.excilys.cdb.dto.UpdateComputerDTO;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.Assert.*;

public class UpdateComputerValidatorTest {
    @Before
    public void loadEnttries() throws IOException, SQLException {
        UTDatabase.getInstance().reload();
    }

    @Test
    public void validWithoutDateAndMannufacturerId() {
        final UpdateComputerDTO updateComputerDTO = new UpdateComputerDTO();
        updateComputerDTO.setId(5L);
        updateComputerDTO.setName("Un nom correct");
        final Result result = UpdateComputerValidator.getInstance().check(updateComputerDTO);
        assertTrue(result.isValid());
    }

    @Test
    public void validWithoutDateWithMannufacturerId() {
        final UpdateComputerDTO updateComputerDTO = new UpdateComputerDTO();
        updateComputerDTO.setId(3L);
        updateComputerDTO.setName("Un nom correct");
        updateComputerDTO.setMannufacturerId(5L);
        final Result result = UpdateComputerValidator.getInstance().check(updateComputerDTO);
        assertTrue(result.isValid());
    }

    @Test
    public void valid() {
        final UpdateComputerDTO updateComputerDTO = new UpdateComputerDTO();
        updateComputerDTO.setId(9L);
        updateComputerDTO.setName("Un nom correct");
        updateComputerDTO.setMannufacturerId(5L);
        updateComputerDTO.setIntroduced(LocalDate.of(2012, 2, 4));
        updateComputerDTO.setDiscontinued(LocalDate.of(2016, 10, 20));
        final Result result = UpdateComputerValidator.getInstance().check(updateComputerDTO);
        assertTrue(result.isValid());
    }

    @Test
    public void unvalidBecauseNameIsEmpty() {
        final UpdateComputerDTO updateComputerDTO = new UpdateComputerDTO();
        updateComputerDTO.setId(3L);
        updateComputerDTO.setName("");
        updateComputerDTO.setMannufacturerId(5L);
        updateComputerDTO.setIntroduced(LocalDate.of(2012, 2, 4));
        updateComputerDTO.setDiscontinued(LocalDate.of(2016, 10, 20));
        final Result result = UpdateComputerValidator.getInstance().check(updateComputerDTO);
        assertFalse(result.isValid());
        assertNotNull(result.getErrors().get("name"));
    }

    @Test
    public void unvalidBecauseMannufacturerDoesNotExist() {
        final UpdateComputerDTO updateComputerDTO = new UpdateComputerDTO();
        updateComputerDTO.setId(3L);
        updateComputerDTO.setName("Un nom correct");
        updateComputerDTO.setMannufacturerId(5300L);
        updateComputerDTO.setIntroduced(LocalDate.of(2012, 2, 4));
        updateComputerDTO.setDiscontinued(LocalDate.of(2016, 10, 20));
        final Result result = UpdateComputerValidator.getInstance().check(updateComputerDTO);
        assertFalse(result.isValid());
        assertNotNull(result.getErrors().get("mannufacturerId"));
    }

    @Test
    public void unvalidBecauseDiscontinuedIsBeforeIntroduced() {
        final UpdateComputerDTO updateComputerDTO = new UpdateComputerDTO();
        updateComputerDTO.setId(3L);
        updateComputerDTO.setName("Un nom correct");
        updateComputerDTO.setMannufacturerId(5L);
        updateComputerDTO.setIntroduced(LocalDate.of(2016, 2, 4));
        updateComputerDTO.setDiscontinued(LocalDate.of(2012, 10, 20));
        final Result result = UpdateComputerValidator.getInstance().check(updateComputerDTO);
        assertFalse(result.isValid());
        assertNotNull(result.getErrors().get("discontinued"));
    }

    @Test
    public void unvalidBecauseComputerDoesNotExist() {
        final UpdateComputerDTO updateComputerDTO = new UpdateComputerDTO();
        updateComputerDTO.setId(984L);
        updateComputerDTO.setName("Un nom correct");
        updateComputerDTO.setMannufacturerId(5L);
        updateComputerDTO.setIntroduced(LocalDate.of(2012, 2, 4));
        updateComputerDTO.setDiscontinued(LocalDate.of(2016, 10, 20));
        final Result result = UpdateComputerValidator.getInstance().check(updateComputerDTO);
        assertFalse(result.isValid());
        assertNotNull(result.getErrors().get("id"));
    }

}
