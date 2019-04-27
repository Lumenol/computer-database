package com.excilys.cdb.validator;

import com.excilys.cdb.TestDatabase;
import com.excilys.cdb.dto.UpdateComputerDTO;
import com.excilys.cdb.validator.Validator.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UpdateComputerValidatorTest {
    @BeforeEach
    public void loadEnttries() throws IOException, SQLException {
        TestDatabase.getInstance().reload();
    }

    @Test
    void validWithoutDateAndMannufacturerId() {
        final UpdateComputerDTO updateComputerDTO = new UpdateComputerDTO();
        updateComputerDTO.setId(5L);
        updateComputerDTO.setName("Un nom correct");
        final Result result = UpdateComputerValidator.getInstance().check(updateComputerDTO);
        assertTrue(result.isValid());
    }

    @Test
    void validWithoutDateWithMannufacturerId() {
        final UpdateComputerDTO updateComputerDTO = new UpdateComputerDTO();
        updateComputerDTO.setId(3L);
        updateComputerDTO.setName("Un nom correct");
        updateComputerDTO.setMannufacturerId(5L);
        final Result result = UpdateComputerValidator.getInstance().check(updateComputerDTO);
        assertTrue(result.isValid());
    }

    @Test
    void valid() {
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
    void unvalidBecauseNameIsEmpty() {
        final UpdateComputerDTO updateComputerDTO = new UpdateComputerDTO();
        updateComputerDTO.setId(3L);
        updateComputerDTO.setName("");
        updateComputerDTO.setMannufacturerId(5L);
        updateComputerDTO.setIntroduced(LocalDate.of(2012, 2, 4));
        updateComputerDTO.setDiscontinued(LocalDate.of(2016, 10, 20));
        final Result result = UpdateComputerValidator.getInstance().check(updateComputerDTO);
        assertFalse(result.isValid());
        assertNotNull(result.get("name"));
    }

    @Test
    void unvalidBecauseMannufacturerDoesNotExist() {
        final UpdateComputerDTO updateComputerDTO = new UpdateComputerDTO();
        updateComputerDTO.setId(3L);
        updateComputerDTO.setName("Un nom correct");
        updateComputerDTO.setMannufacturerId(5300L);
        updateComputerDTO.setIntroduced(LocalDate.of(2012, 2, 4));
        updateComputerDTO.setDiscontinued(LocalDate.of(2016, 10, 20));
        final Result result = UpdateComputerValidator.getInstance().check(updateComputerDTO);
        assertFalse(result.isValid());
        assertNotNull(result.get("mannufacturerId"));
    }

    @Test
    void unvalidBecauseDiscontinuedIsBeforeIntroduced() {
        final UpdateComputerDTO updateComputerDTO = new UpdateComputerDTO();
        updateComputerDTO.setId(3L);
        updateComputerDTO.setName("Un nom correct");
        updateComputerDTO.setMannufacturerId(5L);
        updateComputerDTO.setIntroduced(LocalDate.of(2016, 2, 4));
        updateComputerDTO.setDiscontinued(LocalDate.of(2012, 10, 20));
        final Result result = UpdateComputerValidator.getInstance().check(updateComputerDTO);
        assertFalse(result.isValid());
        assertNotNull(result.get("discontinued"));
    }

    @Test
    void unvalidBecauseComputerDoesNotExist() {
        final UpdateComputerDTO updateComputerDTO = new UpdateComputerDTO();
        updateComputerDTO.setId(984L);
        updateComputerDTO.setName("Un nom correct");
        updateComputerDTO.setMannufacturerId(5L);
        updateComputerDTO.setIntroduced(LocalDate.of(2012, 2, 4));
        updateComputerDTO.setDiscontinued(LocalDate.of(2016, 10, 20));
        final Result result = UpdateComputerValidator.getInstance().check(updateComputerDTO);
        assertFalse(result.isValid());
        assertNotNull(result.get("id"));
    }

}
