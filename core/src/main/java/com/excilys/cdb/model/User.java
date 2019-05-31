package com.excilys.cdb.model;

import java.util.Collections;
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

    public Long getId() {
	return id;
    }

    public String getLogin() {
	return login;
    }

    public String getPassword() {
	return password;
    }

    public Set<Role> getRoles() {
	return Collections.unmodifiableSet(roles);
    }

    public boolean hasRole(Role role) {
	return roles.contains(role);
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
	User other = (User) obj;
	return Objects.equals(id, other.id) && Objects.equals(login, other.login)
		&& Objects.equals(password, other.password) && Objects.equals(roles, other.roles);
    }

    @Override
    public String toString() {
	return "User [id=" + id + ", login=" + login + ", password=" + password + ", roles=" + roles + "]";
    }

    public static class UserBuilder {
	private Long id;
	private String login;
	private String password;
	private Set<Role> roles = new HashSet<>();

	UserBuilder() {
	}

	public UserBuilder clone(User user) {
	    id = user.id;
	    login = user.login;
	    password = user.password;
	    roles = new HashSet<>(user.roles);
	    return this;
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
	    Objects.requireNonNull(role);
	    this.roles.add(role);
	    return this;
	}

	public User build() {
	    return new User(id, login, password, roles);
	}

	@Override
	public String toString() {
	    return "UserBuilder [id=" + id + ", login=" + login + ", password=" + password + ", roles=" + roles + "]";
	}

    }

}
