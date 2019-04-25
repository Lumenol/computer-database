package com.excilys.cdb.model;

import java.util.Objects;

public class Company {

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
    public static CompagnyBuilder builder() {
	return new CompagnyBuilder();
    }

    private final Long id;

    private final String name;

    private Company(Long id, String name) {
	Objects.requireNonNull(name);
	this.id = id;
	this.name = name;
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
	if (id == null) {
	    if (other.id != null)
		return false;
	} else if (!id.equals(other.id))
	    return false;
	if (name == null) {
	    if (other.name != null)
		return false;
	} else if (!name.equals(other.name))
	    return false;
	return true;
    }

    public Long getId() {
	return id;
    }

    public String getName() {
	return name;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((id == null) ? 0 : id.hashCode());
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	return result;
    }

    @Override
    public String toString() {
	return "Compagny [id=" + id + ", name=" + name + "]";
    }

}
