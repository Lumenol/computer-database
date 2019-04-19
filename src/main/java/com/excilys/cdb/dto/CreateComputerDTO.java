package com.excilys.cdb.dto;

import java.time.LocalDate;

public class CreateComputerDTO {
    private LocalDate discontinued;
    private LocalDate introduced;
    private Long mannufacturerId;
    private String name;

    public LocalDate getDiscontinued() {
	return discontinued;
    }

    public LocalDate getIntroduced() {
	return introduced;
    }

    public Long getMannufacturerId() {
	return mannufacturerId;
    }

    public String getName() {
	return name;
    }

    public void setDiscontinued(LocalDate discontinued) {
	this.discontinued = discontinued;
    }

    public void setIntroduced(LocalDate introduced) {
	this.introduced = introduced;
    }

    public void setMannufacturerId(Long mannufacturerId) {
	this.mannufacturerId = mannufacturerId;
    }

    public void setName(String name) {
	this.name = name;
    }
}
