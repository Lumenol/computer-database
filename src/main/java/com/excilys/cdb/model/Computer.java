package com.excilys.cdb.model;

import java.time.LocalDate;

public class Computer {

    public static class ComputerBuilder {
	private LocalDate discontinued;
	private Long id;
	private LocalDate introduced;
	private Company manufacturer;
	private String name;

	public Computer build() {
	    return new Computer(id, name, manufacturer, introduced, discontinued);
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

    private final LocalDate discontinued;
    private final Long id;
    private final LocalDate introduced;

    private final Company manufacturer;

    private final String name;

    private Computer(Long id, String name, Company manufacturer, LocalDate introduced, LocalDate discontinued) {
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
	if (discontinued == null) {
	    if (other.discontinued != null)
		return false;
	} else if (!discontinued.equals(other.discontinued))
	    return false;
	if (id == null) {
	    if (other.id != null)
		return false;
	} else if (!id.equals(other.id))
	    return false;
	if (introduced == null) {
	    if (other.introduced != null)
		return false;
	} else if (!introduced.equals(other.introduced))
	    return false;
	if (manufacturer == null) {
	    if (other.manufacturer != null)
		return false;
	} else if (!manufacturer.equals(other.manufacturer))
	    return false;
	if (name == null) {
	    if (other.name != null)
		return false;
	} else if (!name.equals(other.name))
	    return false;
	return true;
    }

    public LocalDate getDiscontinued() {
	return discontinued;
    }

    public Long getId() {
	return id;
    }

    public LocalDate getIntroduced() {
	return introduced;
    }

    public Company getManufacturer() {
	return manufacturer;
    }

    public String getName() {
	return name;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((discontinued == null) ? 0 : discontinued.hashCode());
	result = prime * result + ((id == null) ? 0 : id.hashCode());
	result = prime * result + ((introduced == null) ? 0 : introduced.hashCode());
	result = prime * result + ((manufacturer == null) ? 0 : manufacturer.hashCode());
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	return result;
    }

    @Override
    public String toString() {
	return "Computer [id=" + id + ", name=" + name + ", manufacturer=" + manufacturer + ", introduced=" + introduced
		+ ", discontinued=" + discontinued + "]";
    }

}
