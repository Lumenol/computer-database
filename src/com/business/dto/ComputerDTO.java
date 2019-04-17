package com.business.dto;

import java.time.LocalDate;

public class ComputerDTO {
    private LocalDate discontinued;
    private long id;
    private LocalDate introduced;

    private CompagnyDTO mannufacturer;

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

    public CompagnyDTO getMannufacturer() {
	return mannufacturer;
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

    public void setMannufacturer(CompagnyDTO mannufacturer) {
	this.mannufacturer = mannufacturer;
    }

    public void setName(String name) {
	this.name = name;
    }
}
