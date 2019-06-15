package com.excilys.cdb.shared.dto;

import java.util.Objects;

public class CreateUserDTO {
    private String login;
    private String password;
    private String passwordCheck;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordCheck() {
        return passwordCheck;
    }

    public void setPasswordCheck(String passwordVerification) {
        this.passwordCheck = passwordVerification;
    }

    @Override
    public String toString() {
        return "CreateUserDTO [login=" + login + ", password=" + password + ", passwordCheck="
                + passwordCheck + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password, passwordCheck);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CreateUserDTO other = (CreateUserDTO) obj;
        return Objects.equals(login, other.login) && Objects.equals(password, other.password)
                && Objects.equals(passwordCheck, other.passwordCheck);
    }

}
