package com.excilys.cdb.persistence.entity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.excilys.cdb.model.Role;

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
    @ManyToMany
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @Override
    public String toString() {
	return "UserEntity [id=" + id + ", login=" + login + ", password=" + password + ", roles=" + roles + "]";
    }

    @Override
    public int hashCode() {
	return Objects.hash(id, login, password, roles);
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
		&& Objects.equals(password, other.password) && Objects.equals(roles, other.roles);
    }

    public UserEntity() {
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

    public Set<Role> getRoles() {
	return roles;
    }

    public void setRoles(Set<Role> roles) {
	this.roles = roles;
    }

    UserEntity(Long id, String login, String password, Set<Role> roles) {
	this.id = id;
	this.login = login;
	this.password = password;
	this.roles = roles;
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
	    Objects.requireNonNull(login);
	    this.login = login;
	    return this;
	}

	public UserEntityBuilder password(String password) {
	    Objects.requireNonNull(password);
	    this.password = password;
	    return this;
	}

	public UserEntityBuilder roles(Set<Role> roles) {
	    Objects.requireNonNull(roles);
	    this.roles = roles;
	    return this;
	}

	public UserEntity build() {
	    return new UserEntity(id, login, password, roles);
	}

	@Override
	public String toString() {
	    return "UserEntityBuilder [id=" + id + ", login=" + login + ", password=" + password + ", roles=" + roles
		    + "]";
	}

    }

}
