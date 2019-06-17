package com.excilys.cdb.shared.validator;

import com.excilys.cdb.shared.configuration.SharedConfigurationTest;
import com.excilys.cdb.shared.dto.CreateComputerDTO;
import com.excilys.cdb.shared.dto.UpdateComputerDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.validation.BindException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SharedConfigurationTest.class)
public class UpdateComputerValidatorTest {

    private UpdateComputerValidator updateComputerValidator;
    private CompanyExistById companyExistByIdMock;
    private ComputerExistById computerExistById;

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

    @BeforeEach
    public void setUp() {
        reset(companyExistByIdMock, computerExistById);
    }

    @Test
    void supportsOk() {
        assertTrue(updateComputerValidator.supports(UpdateComputerDTO.class));
    }

    @Test
    void supportsKo() {
        assertFalse(updateComputerValidator.supports(CreateComputerDTO.class));
    }

    @Test
    public void validWithoutDateAndmanufacturerId() {
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
    public void validWithoutDateWithmanufacturerId() {
        final long id = 3L;
        final long manufacturerId = 5L;
        when(computerExistById.exist(id)).thenReturn(true);
        when(companyExistByIdMock.exist(manufacturerId)).thenReturn(true);
        final UpdateComputerDTO updateComputerDTO = new UpdateComputerDTO();
        updateComputerDTO.setId(id);
        updateComputerDTO.setName("Un nom correct");
        updateComputerDTO.setManufacturerId(manufacturerId);
        final BindException errors = new BindException(updateComputerDTO, "dto");
        updateComputerValidator.validate(updateComputerDTO, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void validWithAllDDate() {
        final long id = 9L;
        final long manufacturerId = 5L;
        when(computerExistById.exist(id)).thenReturn(true);
        when(companyExistByIdMock.exist(manufacturerId)).thenReturn(true);
        final UpdateComputerDTO updateComputerDTO = new UpdateComputerDTO();
        updateComputerDTO.setId(id);
        updateComputerDTO.setName("Un nom correct");
        updateComputerDTO.setManufacturerId(manufacturerId);
        updateComputerDTO.setIntroduced(LocalDate.of(2012, 2, 4));
        updateComputerDTO.setDiscontinued(LocalDate.of(2016, 10, 20));
        final BindException errors = new BindException(updateComputerDTO, "dto");
        updateComputerValidator.validate(updateComputerDTO, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void validWithoutIntroduced() {
        final long id = 9L;
        final long manufacturerId = 5L;
        when(computerExistById.exist(id)).thenReturn(true);
        when(companyExistByIdMock.exist(manufacturerId)).thenReturn(true);
        final UpdateComputerDTO updateComputerDTO = new UpdateComputerDTO();
        updateComputerDTO.setId(id);
        updateComputerDTO.setName("Un nom correct");
        updateComputerDTO.setManufacturerId(manufacturerId);
        updateComputerDTO.setDiscontinued(LocalDate.of(2016, 10, 20));
        final BindException errors = new BindException(updateComputerDTO, "dto");
        updateComputerValidator.validate(updateComputerDTO, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void validWithoutDiscontinued() {
        final long id = 9L;
        final long manufacturerId = 5L;
        when(computerExistById.exist(id)).thenReturn(true);
        when(companyExistByIdMock.exist(manufacturerId)).thenReturn(true);
        final UpdateComputerDTO updateComputerDTO = new UpdateComputerDTO();
        updateComputerDTO.setId(id);
        updateComputerDTO.setName("Un nom correct");
        updateComputerDTO.setManufacturerId(manufacturerId);
        updateComputerDTO.setIntroduced(LocalDate.of(2012, 2, 4));
        final BindException errors = new BindException(updateComputerDTO, "dto");
        updateComputerValidator.validate(updateComputerDTO, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void unvalidBecauseNameIsEmpty() {
        final long id = 3L;
        final long manufacturerId = 5L;
        when(computerExistById.exist(id)).thenReturn(true);
        when(companyExistByIdMock.exist(manufacturerId)).thenReturn(true);
        final UpdateComputerDTO updateComputerDTO = new UpdateComputerDTO();
        updateComputerDTO.setId(id);
        updateComputerDTO.setName("");
        updateComputerDTO.setManufacturerId(manufacturerId);
        updateComputerDTO.setIntroduced(LocalDate.of(2012, 2, 4));
        updateComputerDTO.setDiscontinued(LocalDate.of(2016, 10, 20));
        final BindException errors = new BindException(updateComputerDTO, "dto");
        updateComputerValidator.validate(updateComputerDTO, errors);
        if (!errors.hasFieldErrors("name")) {
            fail("La validation a échoué");
        }
    }

    @Test
    public void unvalidBecauseNameIsNull() {
        final long id = 3L;
        final long manufacturerId = 5L;
        when(computerExistById.exist(id)).thenReturn(true);
        when(companyExistByIdMock.exist(manufacturerId)).thenReturn(true);
        final UpdateComputerDTO updateComputerDTO = new UpdateComputerDTO();
        updateComputerDTO.setId(id);
        updateComputerDTO.setName(null);
        updateComputerDTO.setManufacturerId(manufacturerId);
        updateComputerDTO.setIntroduced(LocalDate.of(2012, 2, 4));
        updateComputerDTO.setDiscontinued(LocalDate.of(2016, 10, 20));
        final BindException errors = new BindException(updateComputerDTO, "dto");
        updateComputerValidator.validate(updateComputerDTO, errors);
        if (!errors.hasFieldErrors("name")) {
            fail("La validation a échoué");
        }
    }

    @Test
    public void unvalidBecausemanufacturerDoesNotExist() {
        final long id = 3L;
        final long manufacturerId = 5L;
        when(computerExistById.exist(id)).thenReturn(true);
        when(companyExistByIdMock.exist(manufacturerId)).thenReturn(false);
        final UpdateComputerDTO updateComputerDTO = new UpdateComputerDTO();
        updateComputerDTO.setId(id);
        updateComputerDTO.setName("Un nom correct");
        updateComputerDTO.setManufacturerId(manufacturerId);
        updateComputerDTO.setIntroduced(LocalDate.of(2012, 2, 4));
        updateComputerDTO.setDiscontinued(LocalDate.of(2016, 10, 20));
        final BindException errors = new BindException(updateComputerDTO, "dto");
        updateComputerValidator.validate(updateComputerDTO, errors);
        if (!errors.hasFieldErrors("manufacturerId")) {
            fail("La validation a échoué");
        }
    }

    @Test
    public void unvalidBecauseDiscontinuedIsBeforeIntroduced() {
        final long id = 3L;
        final long manufacturerId = 5L;
        when(computerExistById.exist(id)).thenReturn(true);
        when(companyExistByIdMock.exist(manufacturerId)).thenReturn(true);
        final UpdateComputerDTO updateComputerDTO = new UpdateComputerDTO();
        updateComputerDTO.setId(id);
        updateComputerDTO.setName("Un nom correct");
        updateComputerDTO.setManufacturerId(manufacturerId);
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
        final long manufacturerId = 5L;
        when(companyExistByIdMock.exist(manufacturerId)).thenReturn(true);
        final UpdateComputerDTO updateComputerDTO = new UpdateComputerDTO();
        updateComputerDTO.setId(null);
        updateComputerDTO.setName("Un nom correct");
        updateComputerDTO.setManufacturerId(manufacturerId);
        updateComputerDTO.setIntroduced(LocalDate.of(2012, 2, 4));
        updateComputerDTO.setDiscontinued(LocalDate.of(2016, 10, 20));
        final BindException errors = new BindException(updateComputerDTO, "dto");
        updateComputerValidator.validate(updateComputerDTO, errors);
        if (!errors.hasFieldErrors("id")) {
            fail("La validation a échoué");
        }
    }

    @Test
    public void unvalidBecauseComputerIdDoesNotExist() {
        final long id = 3L;
        final long manufacturerId = 5L;
        when(computerExistById.exist(id)).thenReturn(false);
        when(companyExistByIdMock.exist(manufacturerId)).thenReturn(true);
        final UpdateComputerDTO updateComputerDTO = new UpdateComputerDTO();
        updateComputerDTO.setId(id);
        updateComputerDTO.setName("Un nom correct");
        updateComputerDTO.setManufacturerId(manufacturerId);
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
        final long manufacturerId = 5L;
        when(computerExistById.exist(id)).thenReturn(true);
        when(companyExistByIdMock.exist(manufacturerId)).thenReturn(true);
        final UpdateComputerDTO updateComputerDTO = new UpdateComputerDTO();
        updateComputerDTO.setId(id);
        updateComputerDTO.setName("Un nom correct");
        updateComputerDTO.setManufacturerId(manufacturerId);
        updateComputerDTO.setIntroduced(LocalDate.of(1969, 10, 20));
        updateComputerDTO.setDiscontinued(LocalDate.of(2016, 10, 20));
        final BindException errors = new BindException(updateComputerDTO, "dto");
        updateComputerValidator.validate(updateComputerDTO, errors);
        if (!errors.hasFieldErrors("introduced")) {
            fail("La validation a échoué");
        }
    }
}
