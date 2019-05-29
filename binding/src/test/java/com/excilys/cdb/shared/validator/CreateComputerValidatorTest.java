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
    private CompanyExistById companyExistByIdMock;

    @Autowired
    public void setCreateComputerValidator(CreateComputerValidator createComputerValidator) {
        this.createComputerValidator = createComputerValidator;
    }

    @Autowired
    public void setCompanyExistByIdMock(CompanyExistById companyExistByIdMock) {
        this.companyExistByIdMock = companyExistByIdMock;
    }

    @Before
    public void setUp() {
        reset(companyExistByIdMock);
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
        createComputerDTO.setmanufacturerId(manufacturerId);
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
        createComputerDTO.setmanufacturerId(manufacturerId);
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
        createComputerDTO.setmanufacturerId(manufacturerId);
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
        createComputerDTO.setmanufacturerId(manufacturerId);
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
        createComputerDTO.setmanufacturerId(manufacturerId);
        createComputerDTO.setIntroduced(LocalDate.of(2016, 2, 4));
        createComputerDTO.setDiscontinued(LocalDate.of(2012, 10, 20));
        final BindException errors = new BindException(createComputerDTO, "dto");
        createComputerValidator.validate(createComputerDTO, errors);
        if (!errors.hasFieldErrors("discontinued")) {
            fail("La validation a échoué");
        }
    }
}
