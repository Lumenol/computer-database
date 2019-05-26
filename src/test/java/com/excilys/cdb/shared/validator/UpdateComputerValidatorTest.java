package com.excilys.cdb.shared.validator;

import com.excilys.cdb.shared.config.SharedConfigTest;
import com.excilys.cdb.shared.dto.UpdateComputerDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.BindException;

import java.time.LocalDate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = SharedConfigTest.class)
public class UpdateComputerValidatorTest {

    private UpdateComputerValidator updateComputerValidator;
    private CompanyExistById companyExistByIdMock;
    @Autowired
    public void setUpdateComputerValidator(UpdateComputerValidator updateComputerValidator) {
        this.updateComputerValidator = updateComputerValidator;
    }

    @Autowired
    public void setCompanyExistByIdMock(CompanyExistById companyExistByIdMock) {
        this.companyExistByIdMock = companyExistByIdMock;
    }

    @Autowired
    public void setComputerExistById(ComputerExistById computerExistById) {
        this.computerExistById = computerExistById;
    }

    private ComputerExistById computerExistById;

    @Before
    public void setUp() {
        reset(companyExistByIdMock, computerExistById);
    }


    @Test
    public void validWithoutDateAndMannufacturerId() {
        final long id = 5L;
        when(computerExistById.exist(id)).thenReturn(true);
        final UpdateComputerDTO updateComputerDTO = new UpdateComputerDTO();
        updateComputerDTO.setId(id);
        updateComputerDTO.setName("Un nom correct");
        final BindException errors = new BindException(updateComputerDTO, "dto");
        updateComputerValidator.validate(updateComputerDTO, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void validWithoutDateWithMannufacturerId() {
        final long id = 3L;
        final long mannufacturerId = 5L;
        when(computerExistById.exist(id)).thenReturn(true);
        when(companyExistByIdMock.exist(mannufacturerId)).thenReturn(true);
        final UpdateComputerDTO updateComputerDTO = new UpdateComputerDTO();
        updateComputerDTO.setId(id);
        updateComputerDTO.setName("Un nom correct");
        updateComputerDTO.setMannufacturerId(mannufacturerId);
        final BindException errors = new BindException(updateComputerDTO, "dto");
        updateComputerValidator.validate(updateComputerDTO, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void valid() {
        final long id = 9L;
        final long mannufacturerId = 5L;
        when(computerExistById.exist(id)).thenReturn(true);
        when(companyExistByIdMock.exist(mannufacturerId)).thenReturn(true);
        final UpdateComputerDTO updateComputerDTO = new UpdateComputerDTO();
        updateComputerDTO.setId(id);
        updateComputerDTO.setName("Un nom correct");
        updateComputerDTO.setMannufacturerId(mannufacturerId);
        updateComputerDTO.setIntroduced(LocalDate.of(2012, 2, 4));
        updateComputerDTO.setDiscontinued(LocalDate.of(2016, 10, 20));
        final BindException errors = new BindException(updateComputerDTO, "dto");
        updateComputerValidator.validate(updateComputerDTO, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void unvalidBecauseNameIsEmpty() {
        final long id = 3L;
        final long mannufacturerId = 5L;
        when(computerExistById.exist(id)).thenReturn(true);
        when(companyExistByIdMock.exist(mannufacturerId)).thenReturn(true);
        final UpdateComputerDTO updateComputerDTO = new UpdateComputerDTO();
        updateComputerDTO.setId(id);
        updateComputerDTO.setName("");
        updateComputerDTO.setMannufacturerId(mannufacturerId);
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
        final long id = 3L;
        final long mannufacturerId = 5L;
        when(computerExistById.exist(id)).thenReturn(true);
        when(companyExistByIdMock.exist(mannufacturerId)).thenReturn(false);
        final UpdateComputerDTO updateComputerDTO = new UpdateComputerDTO();
        updateComputerDTO.setId(id);
        updateComputerDTO.setName("Un nom correct");
        updateComputerDTO.setMannufacturerId(mannufacturerId);
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
        final long id = 3L;
        final long mannufacturerId = 5L;
        when(computerExistById.exist(id)).thenReturn(true);
        when(companyExistByIdMock.exist(mannufacturerId)).thenReturn(true);
        final UpdateComputerDTO updateComputerDTO = new UpdateComputerDTO();
        updateComputerDTO.setId(id);
        updateComputerDTO.setName("Un nom correct");
        updateComputerDTO.setMannufacturerId(mannufacturerId);
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
        final long mannufacturerId = 5L;
        when(companyExistByIdMock.exist(mannufacturerId)).thenReturn(true);
        final UpdateComputerDTO updateComputerDTO = new UpdateComputerDTO();
        updateComputerDTO.setId(null);
        updateComputerDTO.setName("Un nom correct");
        updateComputerDTO.setMannufacturerId(mannufacturerId);
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
        final long id = 3L;
        final long mannufacturerId = 5L;
        when(computerExistById.exist(id)).thenReturn(true);
        when(companyExistByIdMock.exist(mannufacturerId)).thenReturn(true);
        final UpdateComputerDTO updateComputerDTO = new UpdateComputerDTO();
        updateComputerDTO.setId(id);
        updateComputerDTO.setName("Un nom correct");
        updateComputerDTO.setMannufacturerId(mannufacturerId);
        updateComputerDTO.setIntroduced(LocalDate.of(1969, 10, 20));
        updateComputerDTO.setDiscontinued(LocalDate.of(2016, 10, 20));
        final BindException errors = new BindException(updateComputerDTO, "dto");
        updateComputerValidator.validate(updateComputerDTO, errors);
        if (!errors.hasFieldErrors("introduced")) {
            fail("La validation a échoué");
        }
    }
}
