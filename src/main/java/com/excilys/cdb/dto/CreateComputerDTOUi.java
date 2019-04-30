package com.excilys.cdb.dto;

public class CreateComputerDTOUi {
    private String discontinued;
    private String introduced;
    private String mannufacturerId;
    private String name;

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
}
