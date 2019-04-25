package com.excilys.cdb.dto;

import java.time.LocalDate;
import java.util.Objects;

public class ComputerDTO {
    private LocalDate discontinued;
    private long id;
    private LocalDate introduced;
    private CompanyDTO mannufacturer;
    private String name;

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

    public LocalDate getDiscontinued() {
	return discontinued;
    }

    public long getId() {
	return id;
    }

    public LocalDate getIntroduced() {
	return introduced;
    }

    public CompanyDTO getMannufacturer() {
	return mannufacturer;
    }

    public String getName() {
	return name;
    }

    @Override
    public int hashCode() {
	return Objects.hash(discontinued, id, introduced, mannufacturer, name);
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

    public void setMannufacturer(CompanyDTO mannufacturer) {
	this.mannufacturer = mannufacturer;
    }

    public void setName(String name) {
	this.name = name;
    }

    @Override
    public String toString() {
	return "ComputerDTO{" + "discontinued=" + discontinued + ", id=" + id + ", introduced=" + introduced
		+ ", mannufacturer=" + mannufacturer + ", name='" + name + '\'' + '}';
    }
}
