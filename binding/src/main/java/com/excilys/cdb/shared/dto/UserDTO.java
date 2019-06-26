package com.excilys.cdb.shared.dto;

import java.util.Objects;

public class UserDTO {
    private Long id;
    private String login;
    private boolean isAdmin;

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", isAdmin=" + isAdmin +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return isAdmin == userDTO.isAdmin &&
                Objects.equals(id, userDTO.id) &&
                Objects.equals(login, userDTO.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, isAdmin);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
