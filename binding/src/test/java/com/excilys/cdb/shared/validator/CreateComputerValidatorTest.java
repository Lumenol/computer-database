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
public class CreateComputerValidatorTest {

    private CreateComputerValidator createComputerValidator;
    private CompanyExistById companyExistByIdMock;

    @Autowired
    public void setCreateComputerValidator(CreateComputerValidator createComputerValidator) {
        this.createComputerValidator = createComputerValidator;
    }

    @Autowired
    public void setCompanyExistByIdMock(CompanyExistById companyExistByIdMock) {
        this.companyExistByIdMock = companyExistByIdMock;
    }

    @BeforeEach
    public void setUp() {
        reset(companyExistByIdMock);
    }

    @Test
    void supportsOk() {
        assertTrue(createComputerValidator.supports(CreateComputerDTO.class));
    }

    @Test
    void supportsKo() {
        assertFalse(createComputerValidator.supports(UpdateComputerDTO.class));
    }

    @Test
    public void validWithoutDateAndmanufacturerId() {
        final CreateComputerDTO createComputerDTO = new CreateComputerDTO();
        createComputerDTO.setName("Un nom correct");
        final BindException errors = new BindException(createComputerDTO, "dto");
        createComputerValidator.validate(createComputerDTO, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void validWithoutDateWithmanufacturerId() {
        final long manufacturerId = 5L;
        when(companyExistByIdMock.exist(manufacturerId)).thenReturn(true);
        final CreateComputerDTO createComputerDTO = new CreateComputerDTO();
        createComputerDTO.setName("Un nom correct");
        createComputerDTO.setManufacturerId(manufacturerId);
        final BindException errors = new BindException(createComputerDTO, "dto");
        createComputerValidator.validate(createComputerDTO, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void valid() {
        final long manufacturerId = 5L;
        when(companyExistByIdMock.exist(manufacturerId)).thenReturn(true);
        final CreateComputerDTO createComputerDTO = new CreateComputerDTO();
        createComputerDTO.setName("Un nom correct");
        createComputerDTO.setManufacturerId(manufacturerId);
        createComputerDTO.setIntroduced(LocalDate.of(2012, 2, 4));
        createComputerDTO.setDiscontinued(LocalDate.of(2016, 10, 20));
        final BindException errors = new BindException(createComputerDTO, "dto");
        createComputerValidator.validate(createComputerDTO, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void unvalidBecauseNameIsEmpty() {
        final long manufacturerId = 5L;
        when(companyExistByIdMock.exist(manufacturerId)).thenReturn(true);
        final CreateComputerDTO createComputerDTO = new CreateComputerDTO();
        createComputerDTO.setName("");
        createComputerDTO.setManufacturerId(manufacturerId);
        createComputerDTO.setIntroduced(LocalDate.of(2012, 2, 4));
        createComputerDTO.setDiscontinued(LocalDate.of(2016, 10, 20));
        final BindException errors = new BindException(createComputerDTO, "dto");
        createComputerValidator.validate(createComputerDTO, errors);
        if (!errors.hasFieldErrors("name")) {
            fail("La validation a échoué");
        }
    }

    @Test
    public void unvalidBecausemanufacturerDoesNotExist() {
        final long manufacturerId = 5300L;
        when(companyExistByIdMock.exist(manufacturerId)).thenReturn(false);
        final CreateComputerDTO createComputerDTO = new CreateComputerDTO();
        createComputerDTO.setName("Un nom correct");
        createComputerDTO.setManufacturerId(manufacturerId);
        createComputerDTO.setIntroduced(LocalDate.of(2012, 2, 4));
        createComputerDTO.setDiscontinued(LocalDate.of(2016, 10, 20));
        final BindException errors = new BindException(createComputerDTO, "dto");
        createComputerValidator.validate(createComputerDTO, errors);
        if (!errors.hasFieldErrors("manufacturerId")) {
            fail("La validation a échoué");
        }
    }

    @Test
    public void unvalidBecauseDiscontinuedIsBeforeIntroduced() {
        final long manufacturerId = 5L;
        when(companyExistByIdMock.exist(manufacturerId)).thenReturn(true);
        final CreateComputerDTO createComputerDTO = new CreateComputerDTO();
        createComputerDTO.setName("Un nom correct");
        createComputerDTO.setManufacturerId(manufacturerId);
        createComputerDTO.setIntroduced(LocalDate.of(2016, 2, 4));
        createComputerDTO.setDiscontinued(LocalDate.of(2012, 10, 20));
        final BindException errors = new BindException(createComputerDTO, "dto");
        createComputerValidator.validate(createComputerDTO, errors);
        if (!errors.hasFieldErrors("discontinued")) {
            fail("La validation a échoué");
        }
    }

    @Test
    public void unvalidBecauseDiscontinuedIsAfter2038() {
        final long manufacturerId = 5L;
        when(companyExistByIdMock.exist(manufacturerId)).thenReturn(true);
        final CreateComputerDTO createComputerDTO = new CreateComputerDTO();
        createComputerDTO.setName("Un nom correct");
        createComputerDTO.setManufacturerId(manufacturerId);
        createComputerDTO.setIntroduced(LocalDate.of(2016, 2, 4));
        createComputerDTO.setDiscontinued(LocalDate.of(2040, 10, 20));
        final BindException errors = new BindException(createComputerDTO, "dto");
        createComputerValidator.validate(createComputerDTO, errors);
        if (!errors.hasFieldErrors("discontinued")) {
            fail("La validation a échoué");
        }
    }

}
