package com.excilys.cdb.model;

import java.time.LocalDate;
import java.util.Optional;

public class Computer {

    public static class ComputerBuilder {
	private LocalDate discontinued;
	private Long id;
	private LocalDate introduced;
	private Company manufacturer;
	private String name;

	public Computer build() {
	    return new Computer(id, name, Optional.ofNullable(manufacturer), Optional.ofNullable(introduced),
		    Optional.ofNullable(discontinued));
	}

	public ComputerBuilder discontinued(LocalDate discontinued) {
	    this.discontinued = discontinued;
	    return this;
	}

	public ComputerBuilder id(long id) {
	    this.id = id;
	    return this;
	}

	public ComputerBuilder introduced(LocalDate introduced) {
	    this.introduced = introduced;
	    return this;
	}

	public ComputerBuilder manufacturer(Company manufacturer) {
	    this.manufacturer = manufacturer;
	    return this;
	}

	public ComputerBuilder name(String name) {
	    this.name = name;
	    return this;
	}

    }

    public static ComputerBuilder builder() {
	return new ComputerBuilder();
    }

    private final Optional<LocalDate> discontinued;
    private final Long id;
    private final Optional<LocalDate> introduced;
    private final Optional<Company> manufacturer;

    private final String name;

    public Optional<LocalDate> getIntroduced() {
	return introduced;
    }

    public Optional<Company> getManufacturer() {
	return manufacturer;
    }

    private Computer(Long id, String name, Optional<Company> manufacturer, Optional<LocalDate> introduced,
	    Optional<LocalDate> discontinued) {
	super();
	this.discontinued = discontinued;
	this.id = id;
	this.introduced = introduced;
	this.manufacturer = manufacturer;
	this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Computer other = (Computer) obj;
	if (id == null) {
	    if (other.id != null)
		return false;
	} else if (!id.equals(other.id))
	    return false;
	return true;
    }

    public Optional<LocalDate> getDiscontinued() {
	return discontinued;
    }

    public Long getId() {
	return id;
    }

    public String getName() {
	return name;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((id == null) ? 0 : id.hashCode());
	return result;
    }

    @Override
    public String toString() {
	return "Computer [id=" + id + ", name=" + name + ", manufacturer=" + manufacturer + ", introduced=" + introduced
		+ ", discontinued=" + discontinued + "]";
    }

}
