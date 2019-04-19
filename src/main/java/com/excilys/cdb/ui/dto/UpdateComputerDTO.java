package com.excilys.cdb.ui.dto;

public class UpdateComputerDTO {
    private String discontinued;

    private long id;

    private String introduced;

    private String mannufacturer;
    private String name;

    public String getDiscontinued() {
	return discontinued;
    }

    public long getId() {
	return id;
    }

    public String getIntroduced() {
	return introduced;
    }

    public String getMannufacturer() {
	return mannufacturer;
    }

    public String getName() {
	return name;
    }

    public void setDiscontinued(String discontinued) {
	this.discontinued = discontinued;
    }

    public void setId(long id) {
	this.id = id;
    }

    public void setIntroduced(String introduced) {
	this.introduced = introduced;
    }

    public void setMannufacturer(String mannufacturer) {
	this.mannufacturer = mannufacturer;
    }

    public void setName(String name) {
	this.name = name;
    }
}
