package com.excilys.cdb.shared.validator;

import com.excilys.cdb.shared.config.SharedConfigTest;
import com.excilys.cdb.shared.dto.CreateComputerDTO;
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
public class CreateComputerValidatorTest {

    private CreateComputerValidator createComputerValidator;
    @Autowired
    public void setCreateComputerValidator(CreateComputerValidator createComputerValidator) {
        this.createComputerValidator = createComputerValidator;
    }

    @Autowired
    public void setCompanyExistByIdMock(CompanyExistById companyExistByIdMock) {
        this.companyExistByIdMock = companyExistByIdMock;
    }

    private CompanyExistById companyExistByIdMock;

    @Before
    public void setUp() {
        reset(companyExistByIdMock);
    }

    @Test
    public void validWithoutDateAndMannufacturerId() {
        final CreateComputerDTO createComputerDTO = new CreateComputerDTO();
        createComputerDTO.setName("Un nom correct");
        final BindException errors = new BindException(createComputerDTO, "dto");
        createComputerValidator.validate(createComputerDTO, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void validWithoutDateWithMannufacturerId() {
        final long mannufacturerId = 5L;
        when(companyExistByIdMock.exist(mannufacturerId)).thenReturn(true);
        final CreateComputerDTO createComputerDTO = new CreateComputerDTO();
        createComputerDTO.setName("Un nom correct");
        createComputerDTO.setMannufacturerId(mannufacturerId);
        final BindException errors = new BindException(createComputerDTO, "dto");
        createComputerValidator.validate(createComputerDTO, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void valid() {
        final long mannufacturerId = 5L;
        when(companyExistByIdMock.exist(mannufacturerId)).thenReturn(true);
        final CreateComputerDTO createComputerDTO = new CreateComputerDTO();
        createComputerDTO.setName("Un nom correct");
        createComputerDTO.setMannufacturerId(mannufacturerId);
        createComputerDTO.setIntroduced(LocalDate.of(2012, 2, 4));
        createComputerDTO.setDiscontinued(LocalDate.of(2016, 10, 20));
        final BindException errors = new BindException(createComputerDTO, "dto");
        createComputerValidator.validate(createComputerDTO, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void unvalidBecauseNameIsEmpty() {
        final long mannufacturerId = 5L;
        when(companyExistByIdMock.exist(mannufacturerId)).thenReturn(true);
        final CreateComputerDTO createComputerDTO = new CreateComputerDTO();
        createComputerDTO.setName("");
        createComputerDTO.setMannufacturerId(mannufacturerId);
        createComputerDTO.setIntroduced(LocalDate.of(2012, 2, 4));
        createComputerDTO.setDiscontinued(LocalDate.of(2016, 10, 20));
        final BindException errors = new BindException(createComputerDTO, "dto");
        createComputerValidator.validate(createComputerDTO, errors);
        if (!errors.hasFieldErrors("name")) {
            fail("La validation a échoué");
        }
    }

    @Test
    public void unvalidBecauseMannufacturerDoesNotExist() {
        final long mannufacturerId = 5300L;
        when(companyExistByIdMock.exist(mannufacturerId)).thenReturn(false);
        final CreateComputerDTO createComputerDTO = new CreateComputerDTO();
        createComputerDTO.setName("Un nom correct");
        createComputerDTO.setMannufacturerId(mannufacturerId);
        createComputerDTO.setIntroduced(LocalDate.of(2012, 2, 4));
        createComputerDTO.setDiscontinued(LocalDate.of(2016, 10, 20));
        final BindException errors = new BindException(createComputerDTO, "dto");
        createComputerValidator.validate(createComputerDTO, errors);
        if (!errors.hasFieldErrors("mannufacturerId")) {
            fail("La validation a échoué");
        }
    }

    @Test
    public void unvalidBecauseDiscontinuedIsBeforeIntroduced() {
        final long mannufacturerId = 5L;
        when(companyExistByIdMock.exist(mannufacturerId)).thenReturn(true);
        final CreateComputerDTO createComputerDTO = new CreateComputerDTO();
        createComputerDTO.setName("Un nom correct");
        createComputerDTO.setMannufacturerId(mannufacturerId);
        createComputerDTO.setIntroduced(LocalDate.of(2016, 2, 4));
        createComputerDTO.setDiscontinued(LocalDate.of(2012, 10, 20));
        final BindException errors = new BindException(createComputerDTO, "dto");
        createComputerValidator.validate(createComputerDTO, errors);
        if (!errors.hasFieldErrors("discontinued")) {
            fail("La validation a échoué");
        }
    }
}
