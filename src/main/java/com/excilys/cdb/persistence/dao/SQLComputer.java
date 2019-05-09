package com.excilys.cdb.persistence.dao;

import com.excilys.cdb.model.Computer;

import java.sql.Date;
import java.util.Objects;

class SQLComputer {

    private Date discontinued;
    private Long id;
    private Date introduced;
    private Long manufacturerId;
    private String name;

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

    public Date getDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(Date discontinued) {
        this.discontinued = discontinued;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getIntroduced() {
        return introduced;
    }

    public void setIntroduced(Date introduced) {
        this.introduced = introduced;
    }

    public Long getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(Long manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}