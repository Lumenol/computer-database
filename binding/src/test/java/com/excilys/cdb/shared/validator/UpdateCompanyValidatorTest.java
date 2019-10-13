package com.excilys.cdb.shared.validator;

import com.excilys.cdb.shared.configuration.SharedConfigurationTest;
import com.excilys.cdb.shared.dto.CreateCompanyDTO;
import com.excilys.cdb.shared.dto.UpdateCompanyDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.validation.BindException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SharedConfigurationTest.class)
class UpdateCompanyValidatorTest {
    private UpdateCompanyValidator updateCompanyDTOValidator;
    private CompanyExistById companyExistByIdMock;


    @Autowired
    public void setUpdateCompanyDTOValidator(UpdateCompanyValidator updateCompanyDTOValidator) {
        this.updateCompanyDTOValidator = updateCompanyDTOValidator;
    }

    @Autowired
    public void setCompanyExistByIdMock(CompanyExistById companyExistByIdMock) {
        this.companyExistByIdMock = companyExistByIdMock;
    }

    @Test
    void supportsOk() {
        assertTrue(updateCompanyDTOValidator.supports(UpdateCompanyDTO.class));
    }

    @Test
    void supportsKo() {
        assertFalse(updateCompanyDTOValidator.supports(CreateCompanyDTO.class));
    }

    @Test
    void validateOk() {
        long id = 4;
        when(companyExistByIdMock.exist(id)).thenReturn(true);
        final UpdateCompanyDTO dto = new UpdateCompanyDTO();
        dto.setId(id);
        dto.setName("CompanyName");
        final BindException errors = new BindException(dto, "dto");
        updateCompanyDTOValidator.validate(dto, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    void validateFailIdNotExcist() {
        long id = 4;
        when(companyExistByIdMock.exist(id)).thenReturn(false);
        final UpdateCompanyDTO dto = new UpdateCompanyDTO();
        dto.setId(id);
        dto.setName("CompanyName");
        final BindException errors = new BindException(dto, "dto");
        updateCompanyDTOValidator.validate(dto, errors);
        assertTrue(errors.hasFieldErrors("id"));
    }

    @Test
    void validateFailIdNull() {
        final UpdateCompanyDTO dto = new UpdateCompanyDTO();
        dto.setId(null);
        dto.setName("CompanyName");
        final BindException errors = new BindException(dto, "dto");
        updateCompanyDTOValidator.validate(dto, errors);
        assertTrue(errors.hasFieldErrors("id"));
    }

    @Test
    void validateFailNameEmpty() {
        long id = 4;
        when(companyExistByIdMock.exist(id)).thenReturn(true);
        final UpdateCompanyDTO dto = new UpdateCompanyDTO();
        dto.setId(id);
        dto.setName("");
        final BindException errors = new BindException(dto, "dto");
        updateCompanyDTOValidator.validate(dto, errors);
        assertTrue(errors.hasFieldErrors("name"));
    }

    @Test
    void validateFailNameNull() {
        long id = 4;
        when(companyExistByIdMock.exist(id)).thenReturn(true);
        final UpdateCompanyDTO dto = new UpdateCompanyDTO();
        dto.setId(id);
        dto.setName(null);
        final BindException errors = new BindException(dto, "dto");
        updateCompanyDTOValidator.validate(dto, errors);
        assertTrue(errors.hasFieldErrors("name"));
    }
}
