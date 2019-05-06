package com.excilys.cdb.dto;

import java.util.Objects;

public class CreateCompanyDTO {
    private String name;

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    private CreateCompanyDTO(String name) {
	super();
	this.name = name;
    }

    public static CreateCompanyDTOBuilder builder() {
	return new CreateCompanyDTOBuilder();
    }

    @Override
    public String toString() {
	return "CreateCompanyDTO [name=" + name + "]";
    }

    @Override
    public int hashCode() {
	return Objects.hash(name);
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	CreateCompanyDTO other = (CreateCompanyDTO) obj;
	return Objects.equals(name, other.name);
    }

    public static class CreateCompanyDTOBuilder {
	private String name;

	CreateCompanyDTOBuilder() {
	}

	public CreateCompanyDTOBuilder name(String name) {
	    this.name = name;
	    return this;
	}

	public CreateCompanyDTO build() {
	    return new CreateCompanyDTO(name);
	}

	@Override
	public String toString() {
	    return "CreateCompanyDTO.CreateCompanyDTOBuilder(name=" + this.name + ")";
	}
    }
}
