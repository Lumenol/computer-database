package com.excilys.cdb.persistence.entity;

import javax.persistence.*;
import java.util.Objects;

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

    public UserEntity() {
    }

    UserEntity(Long id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public static UserEntityBuilder builder() {
        return new UserEntityBuilder();
    }

    @Override
    public String toString() {
        return "UserEntity [id=" + id + ", login=" + login + ", password=" + password + "]";
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
        UserEntity other = (UserEntity) obj;
        return Objects.equals(id, other.id) && Objects.equals(login, other.login)
                && Objects.equals(password, other.password);
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

        public UserEntity build() {
            return new UserEntity(id, login, password);
        }

        public String toString() {
            return "UserEntity.UserEntityBuilder(id=" + this.id + ", login=" + this.login + ", password=" + this.password + ")";
        }
    }
}
