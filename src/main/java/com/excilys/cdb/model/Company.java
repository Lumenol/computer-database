package com.excilys.cdb.model;

import java.util.Objects;

public class Company {

    private final Long id;
    private final String name;

    private Company(Long id, String name) {
	Objects.requireNonNull(name);
	this.id = id;
	this.name = name;
    }

    public static CompagnyBuilder builder() {
	return new CompagnyBuilder();
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Company other = (Company) obj;
	return Objects.equals(id, other.id) && Objects.equals(name, other.name);
    }

    public Long getId() {
	return id;
    }

    public String getName() {
	return name;
    }

    @Override
    public int hashCode() {
	return Objects.hash(id, name);
    }

    @Override
    public String toString() {
	return "Compagny [id=" + id + ", name=" + name + "]";
    }

    public static class CompagnyBuilder {

	private Long id;

	private String name;

	private CompagnyBuilder() {
	}

	public Company build() {
	    return new Company(id, name);
	}

	public CompagnyBuilder id(long id) {
	    this.id = id;
	    return this;
	}

	public CompagnyBuilder name(String name) {
	    this.name = name;
	    return this;
	}

    }

}
