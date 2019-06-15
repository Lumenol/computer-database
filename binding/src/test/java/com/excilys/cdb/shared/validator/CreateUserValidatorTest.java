package com.excilys.cdb.shared.validator;

import com.excilys.cdb.shared.configuration.SharedConfigurationTest;
import com.excilys.cdb.shared.dto.CreateUserDTO;
import com.excilys.cdb.shared.dto.UpdateComputerDTO;
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
class CreateUserValidatorTest {

    private Validator<CreateUserDTO> createUserValidator;
    private UserExistByLogin userExistByLoginMock;

    @Autowired
    public void setCreateUserValidator(Validator<CreateUserDTO> createUserValidator) {
        this.createUserValidator = createUserValidator;
    }

    @Autowired
    public void setUserExistByLoginMock(UserExistByLogin userExistByLoginMock) {
        this.userExistByLoginMock = userExistByLoginMock;
    }

    @Test
    void supportsOk() {
        assertTrue(createUserValidator.supports(CreateUserDTO.class));
    }

    @Test
    void supportsKo() {
        assertFalse(createUserValidator.supports(UpdateComputerDTO.class));
    }

    @Test
    void validateOk() {
        final CreateUserDTO createUserDTO = new CreateUserDTO();
        createUserDTO.setLogin("login");
        createUserDTO.setPassword("password");
        createUserDTO.setPasswordCheck("password");
        final BindException errors = new BindException(createUserDTO, "dto");
        createUserValidator.validate(createUserDTO, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    void validateFailLoginEmpty() {
        final CreateUserDTO createUserDTO = new CreateUserDTO();
        createUserDTO.setLogin("");
        createUserDTO.setPassword("password");
        createUserDTO.setPasswordCheck("password");
        final BindException errors = new BindException(createUserDTO, "dto");
        createUserValidator.validate(createUserDTO, errors);
        assertTrue(errors.hasFieldErrors("login"));
    }

    @Test
    void validateFailLoginNull() {
        final CreateUserDTO createUserDTO = new CreateUserDTO();
        createUserDTO.setLogin(null);
        createUserDTO.setPassword("password");
        createUserDTO.setPasswordCheck("password");
        final BindException errors = new BindException(createUserDTO, "dto");
        createUserValidator.validate(createUserDTO, errors);
        assertTrue(errors.hasFieldErrors("login"));
    }

    @Test
    void validateFailLoginAlreadyExist() {
        final String login = "username";
        when(userExistByLoginMock.existByLogin(login)).thenReturn(true);

        final CreateUserDTO createUserDTO = new CreateUserDTO();
        createUserDTO.setLogin(login);
        createUserDTO.setPassword("password");
        createUserDTO.setPasswordCheck("password");
        final BindException errors = new BindException(createUserDTO, "dto");
        createUserValidator.validate(createUserDTO, errors);
        assertTrue(errors.hasFieldErrors("login"));
    }


    @Test
    void validateFailPasswordEmpty() {
        final CreateUserDTO createUserDTO = new CreateUserDTO();
        createUserDTO.setLogin("login");
        createUserDTO.setPassword("");
        createUserDTO.setPasswordCheck("");
        final BindException errors = new BindException(createUserDTO, "dto");
        createUserValidator.validate(createUserDTO, errors);
        assertTrue(errors.hasFieldErrors("password"));
    }

    @Test
    void validateFailPasswordNull() {
        final CreateUserDTO createUserDTO = new CreateUserDTO();
        createUserDTO.setLogin("login");
        createUserDTO.setPassword(null);
        createUserDTO.setPasswordCheck("");
        final BindException errors = new BindException(createUserDTO, "dto");
        createUserValidator.validate(createUserDTO, errors);
        assertTrue(errors.hasFieldErrors("password"));
    }

    @Test
    void validateFailPasswordNotSame() {
        final CreateUserDTO createUserDTO = new CreateUserDTO();
        createUserDTO.setLogin("login");
        createUserDTO.setPassword("pass1");
        createUserDTO.setPasswordCheck("pass2");
        final BindException errors = new BindException(createUserDTO, "dto");
        createUserValidator.validate(createUserDTO, errors);
        assertTrue(errors.hasFieldErrors("passwordCheck"));
    }
}