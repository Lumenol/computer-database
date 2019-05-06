package com.excilys.cdb.dto;

import java.util.Objects;

public class UpdateComputerDTO {
    private String id;
    private String discontinued;
    private String introduced;
    private String mannufacturerId;
    private String name;

    public UpdateComputerDTO() {
    }

    UpdateComputerDTO(String id, String discontinued, String introduced, String mannufacturerId, String name) {
	this.id = id;
	this.discontinued = discontinued;
	this.introduced = introduced;
	this.mannufacturerId = mannufacturerId;
	this.name = name;
    }

    public static UpdateComputerDTOBuilder builder() {
	return new UpdateComputerDTOBuilder();
    }

    @Override
    public String toString() {
	return "UpdateComputerDTO{" + "id='" + id + '\'' + ", discontinued='" + discontinued + '\'' + ", introduced='"
		+ introduced + '\'' + ", mannufacturerId='" + mannufacturerId + '\'' + ", name='" + name + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
	if (this == o)
	    return true;
	if (o == null || getClass() != o.getClass())
	    return false;
	UpdateComputerDTO that = (UpdateComputerDTO) o;
	return Objects.equals(id, that.id) && Objects.equals(discontinued, that.discontinued)
		&& Objects.equals(introduced, that.introduced) && Objects.equals(mannufacturerId, that.mannufacturerId)
		&& Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
	return Objects.hash(id, discontinued, introduced, mannufacturerId, name);
    }

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public String getDiscontinued() {
	return discontinued;
    }

    public void setDiscontinued(String discontinued) {
	this.discontinued = discontinued;
    }

    public String getIntroduced() {
	return introduced;
    }

    public void setIntroduced(String introduced) {
	this.introduced = introduced;
    }

    public String getMannufacturerId() {
	return mannufacturerId;
    }

    public void setMannufacturerId(String mannufacturerId) {
	this.mannufacturerId = mannufacturerId;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public static class UpdateComputerDTOBuilder {
	private String id;
	private String discontinued;
	private String introduced;
	private String mannufacturerId;
	private String name;

	UpdateComputerDTOBuilder() {
	}

	public UpdateComputerDTOBuilder id(String id) {
	    this.id = id;
	    return this;
	}

	public UpdateComputerDTOBuilder discontinued(String discontinued) {
	    this.discontinued = discontinued;
	    return this;
	}

	public UpdateComputerDTOBuilder introduced(String introduced) {
	    this.introduced = introduced;
	    return this;
	}

	public UpdateComputerDTOBuilder mannufacturerId(String mannufacturerId) {
	    this.mannufacturerId = mannufacturerId;
	    return this;
	}

	public UpdateComputerDTOBuilder name(String name) {
	    this.name = name;
	    return this;
	}

	public UpdateComputerDTO build() {
	    return new UpdateComputerDTO(id, discontinued, introduced, mannufacturerId, name);
	}

	public String toString() {
	    return "UpdateComputerDTO.UpdateComputerDTOBuilder(id=" + this.id + ", discontinued=" + this.discontinued
		    + ", introduced=" + this.introduced + ", mannufacturerId=" + this.mannufacturerId + ", name="
		    + this.name + ")";
	}
    }
}
