package com.excilys.cdb.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class User {
    private Long id;
    private String login;
    private String password;
    private Set<Role> roles;

    User(Long id, String login, String password, Set<Role> roles) {
        Objects.requireNonNull(login);
        Objects.requireNonNull(password);
        Objects.requireNonNull(roles);
        this.id = id;
        this.login = login;
        this.password = password;
        this.roles = roles;
    }

    public User() {
        this.roles = new HashSet<>();
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public boolean addRole(Role role) {
        return roles.add(role);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(login, user.login) &&
                Objects.equals(password, user.password) &&
                Objects.equals(roles, user.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, roles);
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

    public static class UserBuilder {
        private Long id;
        private String login;
        private String password;
        private Set<Role> roles = new HashSet<>();

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

        public UserBuilder addRole(Role role) {
            roles.add(role);
            return this;
        }

        public User build() {
            return new User(id, login, password, roles);
        }

        public String toString() {
            return "User.UserBuilder(id=" + this.id + ", login=" + this.login + ", password=" + this.password + ", roles=" + this.roles + ")";
        }
    }
}
