package com.excilys.cdb.dao;

import java.sql.Date;
import java.util.Objects;

import com.excilys.cdb.model.Computer;

class SQLComputer {

    static SQLComputer from(Computer computer) {
	final SQLComputer sqlComputer = new SQLComputer();
	sqlComputer.setId(computer.getId());
	sqlComputer.setName(computer.getName());
	if (Objects.nonNull(computer.getManufacturer())) {
	    sqlComputer.setManufacturerId(computer.getManufacturer().getId());
	}
	if (Objects.nonNull(computer.getIntroduced())) {
	    sqlComputer.setIntroduced(Date.valueOf(computer.getIntroduced()));
	}
	if (Objects.nonNull(computer.getDiscontinued())) {
	    sqlComputer.setDiscontinued(Date.valueOf(computer.getDiscontinued()));
	}
	return sqlComputer;
    }
    private Date discontinued;
    private Long id;
    private Date introduced;
    private Long manufacturerId;

    private String name;

    public Date getDiscontinued() {
	return discontinued;
    }

    public Long getId() {
	return id;
    }

    public Date getIntroduced() {
	return introduced;
    }

    public Long getManufacturerId() {
	return manufacturerId;
    }

    public String getName() {
	return name;
    }

    public void setDiscontinued(Date discontinued) {
	this.discontinued = discontinued;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public void setIntroduced(Date introduced) {
	this.introduced = introduced;
    }

    public void setManufacturerId(Long manufacturerId) {
	this.manufacturerId = manufacturerId;
    }

    public void setName(String name) {
	this.name = name;
    }
}