package com.excilys.cdb.validator;

import com.excilys.cdb.TestDatabase;
import com.excilys.cdb.dto.CreateComputerDTO;
import com.excilys.cdb.validator.Validator.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CreateComputerValidatorTest {

    @BeforeEach
    public void loadEnttries() throws IOException, SQLException {
        TestDatabase.getInstance().reload();
    }

    @Test
    void validWithoutDateAndMannufacturerId() {
        final CreateComputerDTO createComputerDTO = new CreateComputerDTO();
        createComputerDTO.setName("Un nom correct");
        final Result result = CreateComputerValidator.getInstance().check(createComputerDTO);
        assertTrue(result.isValid());
    }

    @Test
    void validWithoutDateWithMannufacturerId() {
        final CreateComputerDTO createComputerDTO = new CreateComputerDTO();
        createComputerDTO.setName("Un nom correct");
        createComputerDTO.setMannufacturerId(5L);
        final Result result = CreateComputerValidator.getInstance().check(createComputerDTO);
        assertTrue(result.isValid());
    }

    @Test
    void valid() {
        final CreateComputerDTO createComputerDTO = new CreateComputerDTO();
        createComputerDTO.setName("Un nom correct");
        createComputerDTO.setMannufacturerId(5L);
        createComputerDTO.setIntroduced(LocalDate.of(2012, 2, 4));
        createComputerDTO.setDiscontinued(LocalDate.of(2016, 10, 20));
        final Result result = CreateComputerValidator.getInstance().check(createComputerDTO);
        assertTrue(result.isValid());
    }

    @Test
    void unvalidBecauseNameIsEmpty() {
        final CreateComputerDTO createComputerDTO = new CreateComputerDTO();
        createComputerDTO.setName("");
        createComputerDTO.setMannufacturerId(5L);
        createComputerDTO.setIntroduced(LocalDate.of(2012, 2, 4));
        createComputerDTO.setDiscontinued(LocalDate.of(2016, 10, 20));
        final Result result = CreateComputerValidator.getInstance().check(createComputerDTO);
        assertFalse(result.isValid());
        assertNotNull(result.get("name"));
    }

    @Test
    void unvalidBecauseMannufacturerDoesNotExist() {
        final CreateComputerDTO createComputerDTO = new CreateComputerDTO();
        createComputerDTO.setName("Un nom correct");
        createComputerDTO.setMannufacturerId(5300L);
        createComputerDTO.setIntroduced(LocalDate.of(2012, 2, 4));
        createComputerDTO.setDiscontinued(LocalDate.of(2016, 10, 20));
        final Result result = CreateComputerValidator.getInstance().check(createComputerDTO);
        assertFalse(result.isValid());
        assertNotNull(result.get("mannufacturerId"));
    }

    @Test
    void unvalidBecauseDiscontinuedIsBeforeIntroduced() {
        final CreateComputerDTO createComputerDTO = new CreateComputerDTO();
        createComputerDTO.setName("Un nom correct");
        createComputerDTO.setMannufacturerId(5L);
        createComputerDTO.setIntroduced(LocalDate.of(2016, 2, 4));
        createComputerDTO.setDiscontinued(LocalDate.of(2012, 10, 20));
        final Result result = CreateComputerValidator.getInstance().check(createComputerDTO);
        assertFalse(result.isValid());
        assertNotNull(result.get("discontinued"));
    }

}
