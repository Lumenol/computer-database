package com.business.dto;

import java.time.LocalDate;
import java.util.Optional;

public class ComputerDTO {
    private Optional<LocalDate> discontinued;
    private long id;
    private Optional<LocalDate> introduced;

    private Optional<CompanyDTO> mannufacturer;

    private String name;

    public Optional<LocalDate> getDiscontinued() {
	return discontinued;
    }

    public void setDiscontinued(Optional<LocalDate> discontinued) {
	this.discontinued = discontinued;
    }

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

    public Optional<LocalDate> getIntroduced() {
	return introduced;
    }

    public void setIntroduced(Optional<LocalDate> introduced) {
	this.introduced = introduced;
    }

    public Optional<CompanyDTO> getMannufacturer() {
	return mannufacturer;
    }

    public void setMannufacturer(Optional<CompanyDTO> mannufacturer) {
	this.mannufacturer = mannufacturer;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }
}
