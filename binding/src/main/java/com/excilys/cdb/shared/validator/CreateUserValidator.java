package com.excilys.cdb.shared.validator;

import com.excilys.cdb.shared.dto.CreateUserDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.util.Objects;

@Component
public class CreateUserValidator implements Validator<CreateUserDTO> {

    private final UserExistByLogin userExistByLogin;

    public CreateUserValidator(UserExistByLogin userExistByLogin) {
        this.userExistByLogin = userExistByLogin;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return CreateUserDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Objects.requireNonNull(target);
        CreateUserDTO toValidate = (CreateUserDTO) target;
        checkLogin(toValidate.getLogin(), errors);
        checkPassword(toValidate.getPassword(), errors);
        checkPasswordAreSame(toValidate.getPassword(), toValidate.getPasswordCheck(), errors);
    }

    private void checkPasswordAreSame(String password, String passwordCheck, Errors errors) {
        if (Objects.nonNull(password) && !password.equals(passwordCheck)) {
            errors.rejectValue("passwordCheck", "validation.passwordCheck.notSame");
        }
    }

    private void checkPassword(String password, Errors errors) {
        if (Objects.isNull(password) || password.trim().isEmpty()) {
            errors.rejectValue("password", "validation.password.blank");
        }
    }

    private void checkLogin(String login, Errors errors) {
        if (login.trim().isEmpty()) {
            errors.rejectValue("login", "validation.login.blank");
        } else if (userExistByLogin.existByLogin(login)) {
            errors.rejectValue("login", "validation.login.alreadyExist");
        }
    }

}
