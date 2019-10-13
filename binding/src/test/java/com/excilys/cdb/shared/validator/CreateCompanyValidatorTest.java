package com.excilys.cdb.shared.validator;

import com.excilys.cdb.shared.configuration.SharedConfigurationTest;
import com.excilys.cdb.shared.dto.CreateCompanyDTO;
import com.excilys.cdb.shared.dto.UpdateComputerDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.validation.BindException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SharedConfigurationTest.class)
class CreateCompanyValidatorTest {
    private CreateCompanyValidator createCompanyDTOValidator;

    @Autowired
    public void setCreateCompanyDTOValidator(CreateCompanyValidator createCompanyDTOValidator) {
        this.createCompanyDTOValidator = createCompanyDTOValidator;
    }

    @Test
    void supportsOk() {
        assertTrue(createCompanyDTOValidator.supports(CreateCompanyDTO.class));
    }

    @Test
    void supportsKo() {
        assertFalse(createCompanyDTOValidator.supports(UpdateComputerDTO.class));
    }

    @Test
    void validateOk() {
        final CreateCompanyDTO createCompanyDTO = new CreateCompanyDTO();
        createCompanyDTO.setName("CompanyName");
        final BindException errors = new BindException(createCompanyDTO, "dto");
        createCompanyDTOValidator.validate(createCompanyDTO, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    void validateFailNameEmpty() {
        final CreateCompanyDTO createCompanyDTO = new CreateCompanyDTO();
        createCompanyDTO.setName("");
        final BindException errors = new BindException(createCompanyDTO, "dto");
        createCompanyDTOValidator.validate(createCompanyDTO, errors);
        assertTrue(errors.hasFieldErrors("name"));
    }

    @Test
    void validateFailNameNull() {
        final CreateCompanyDTO createCompanyDTO = new CreateCompanyDTO();
        createCompanyDTO.setName(null);
        final BindException errors = new BindException(createCompanyDTO, "dto");
        createCompanyDTOValidator.validate(createCompanyDTO, errors);
        assertTrue(errors.hasFieldErrors("name"));
    }
}
