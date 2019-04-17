package com.metier.dto;

import java.time.LocalDate;

public class UpdateComputerDTO {
    private LocalDate discontinued;
    private long id;
    private LocalDate introduced;
    private long mannufacturerId;
    private String name;

    public LocalDate getDiscontinued() {
	return discontinued;
    }

    public long getId() {
	return id;
    }

    public LocalDate getIntroduced() {
	return introduced;
    }

    public long getMannufacturerId() {
	return mannufacturerId;
    }

    public String getName() {
	return name;
    }

    public void setDiscontinued(LocalDate discontinued) {
	this.discontinued = discontinued;
    }

    public void setId(long id) {
	this.id = id;
    }

    public void setIntroduced(LocalDate introduced) {
	this.introduced = introduced;
    }

    public void setMannufacturerId(long mannufacturerId) {
	this.mannufacturerId = mannufacturerId;
    }

    public void setName(String name) {
	this.name = name;
    }
}
