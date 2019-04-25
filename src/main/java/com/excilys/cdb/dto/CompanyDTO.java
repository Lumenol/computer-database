package com.excilys.cdb.dto;

import java.util.Objects;

public class CompanyDTO {
    private long id;
    private String name;

    @Override
    public boolean equals(Object o) {
	if (this == o)
	    return true;
	if (o == null || getClass() != o.getClass())
	    return false;
	CompanyDTO that = (CompanyDTO) o;
	return id == that.id && Objects.equals(name, that.name);
    }

    public long getId() {
	return id;
    }

    public String getName() {
	return name;
    }

    @Override
    public int hashCode() {
	return Objects.hash(id, name);
    }

    public void setId(long id) {
	this.id = id;
    }

    public void setName(String name) {
	this.name = name;
    }

    @Override
    public String toString() {
	return "CompanyDTO{" + "id=" + id + ", name='" + name + '\'' + '}';
    }
}
