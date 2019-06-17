package com.excilys.cdb.persistence.entity;

import com.excilys.cdb.model.Role;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String login;
    @Column(nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    private Set<Role> roles;

    public UserEntity() {
    }

    UserEntity(Long id, String login, String password, Set<Role> roles) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.roles = roles;
    }

    public static UserEntityBuilder builder() {
        return new UserEntityBuilder();
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(login, that.login) &&
                Objects.equals(password, that.password) &&
                Objects.equals(roles, that.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, roles);
    }

    @Override
    public String toString() {
        return "UserEntity [id=" + id + ", login=" + login + ", password=" + password + "]";
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

    public static class UserEntityBuilder {
        private Long id;
        private String login;
        private String password;
        private Set<Role> roles = new HashSet<>();


        UserEntityBuilder() {
        }

        public UserEntityBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public UserEntityBuilder login(String login) {
            this.login = login;
            return this;
        }

        public UserEntityBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserEntityBuilder addRole(Role role) {
            roles.add(role);
            return this;
        }

        public UserEntity build() {
            return new UserEntity(id, login, password, roles);
        }

        public String toString() {
            return "UserEntity.UserEntityBuilder(id=" + this.id + ", login=" + this.login + ", password=" + this.password + ", roles=" + this.roles + ")";
        }
    }
}
