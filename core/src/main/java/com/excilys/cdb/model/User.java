package com.excilys.cdb.model;

import java.util.Objects;

public class User {
    private Long id;
    private String login;
    private String password;

    User(Long id, String login, String password) {
        Objects.requireNonNull(login);
        Objects.requireNonNull(password);
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public User() {
    }

    public static UserBuilder builder() {
        return new UserBuilder();
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        return Objects.equals(id, other.id) && Objects.equals(login, other.login)
                && Objects.equals(password, other.password);
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", login=" + login + ", password=" + password + "]";
    }

    public static class UserBuilder {
        private Long id;
        private String login;
        private String password;

        UserBuilder() {
        }

        public UserBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public UserBuilder login(String login) {
            Objects.requireNonNull(login);
            this.login = login;
            return this;
        }

        public UserBuilder password(String password) {
            Objects.requireNonNull(password);
            this.password = password;
            return this;
        }

        public User build() {
            return new User(id, login, password);
        }

        public String toString() {
            return "User.UserBuilder(id=" + this.id + ", login=" + this.login + ", password=" + this.password + ")";
        }
    }
}
