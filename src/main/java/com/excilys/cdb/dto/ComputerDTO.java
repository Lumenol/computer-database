package com.excilys.cdb.dto;

import java.util.Objects;

public class ComputerDTO {
    private String discontinued;
    private long id;
    private String introduced;
    private String mannufacturer;
    private String name;

    public ComputerDTO() {
    }

    ComputerDTO(String discontinued, long id, String introduced, String mannufacturer, String name) {
	this.discontinued = discontinued;
	this.id = id;
	this.introduced = introduced;
	this.mannufacturer = mannufacturer;
	this.name = name;
    }

    public static ComputerDTOBuilder builder() {
	return new ComputerDTOBuilder();
    }

    @Override
    public String toString() {
	return "ComputerDTO{" + "discontinued='" + discontinued + '\'' + ", id=" + id + ", introduced='" + introduced
		+ '\'' + ", mannufacturer='" + mannufacturer + '\'' + ", name='" + name + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
	if (this == o)
	    return true;
	if (o == null || getClass() != o.getClass())
	    return false;
	ComputerDTO that = (ComputerDTO) o;
	return id == that.id && Objects.equals(discontinued, that.discontinued)
		&& Objects.equals(introduced, that.introduced) && Objects.equals(mannufacturer, that.mannufacturer)
		&& Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
	return Objects.hash(discontinued, id, introduced, mannufacturer, name);
    }

    public String getDiscontinued() {
	return discontinued;
    }

    public void setDiscontinued(String discontinued) {
	this.discontinued = discontinued;
    }

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

    public String getIntroduced() {
	return introduced;
    }

    public void setIntroduced(String introduced) {
	this.introduced = introduced;
    }

    public String getMannufacturer() {
	return mannufacturer;
    }

    public void setMannufacturer(String mannufacturer) {
	this.mannufacturer = mannufacturer;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public static class ComputerDTOBuilder {
	private String discontinued;
	private long id;
	private String introduced;
	private String mannufacturer;
	private String name;

	ComputerDTOBuilder() {
	}

	public ComputerDTOBuilder discontinued(String discontinued) {
	    this.discontinued = discontinued;
	    return this;
	}

	public ComputerDTOBuilder id(long id) {
	    this.id = id;
	    return this;
	}

	public ComputerDTOBuilder introduced(String introduced) {
	    this.introduced = introduced;
	    return this;
	}

	public ComputerDTOBuilder mannufacturer(String mannufacturer) {
	    this.mannufacturer = mannufacturer;
	    return this;
	}

	public ComputerDTOBuilder name(String name) {
	    this.name = name;
	    return this;
	}

	public ComputerDTO build() {
	    return new ComputerDTO(discontinued, id, introduced, mannufacturer, name);
	}

	public String toString() {
	    return "ComputerDTO.ComputerDTOBuilder(discontinued=" + this.discontinued + ", id=" + this.id
		    + ", introduced=" + this.introduced + ", mannufacturer=" + this.mannufacturer + ", name="
		    + this.name + ")";
	}
    }
}
