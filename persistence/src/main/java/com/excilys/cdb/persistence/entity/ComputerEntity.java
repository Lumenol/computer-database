package com.excilys.cdb.persistence.entity;

import java.sql.Timestamp;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "computer")
public class ComputerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Timestamp discontinued;
    private Timestamp introduced;
    @ManyToOne(cascade = { CascadeType.DETACH, CascadeType.REFRESH })
    @JoinColumn(name = "company_id")
    private CompanyEntity manufacturer;
    @Column(nullable = false)
    private String name;

    public ComputerEntity() {
    }

    ComputerEntity(Timestamp discontinued, Long id, Timestamp introduced, CompanyEntity manufacturer, String name) {
	this.discontinued = discontinued;
	this.id = id;
	this.introduced = introduced;
	this.manufacturer = manufacturer;
	this.name = name;
    }

    public static ComputerEntityBuilder builder() {
	return new ComputerEntityBuilder();
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	ComputerEntity other = (ComputerEntity) obj;
	return Objects.equals(discontinued, other.discontinued) && Objects.equals(id, other.id)
		&& Objects.equals(introduced, other.introduced) && Objects.equals(manufacturer, other.manufacturer)
		&& Objects.equals(name, other.name);
    }

    public Timestamp getDiscontinued() {
	return discontinued;
    }

    public void setDiscontinued(Timestamp discontinued) {
	this.discontinued = discontinued;
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public Timestamp getIntroduced() {
	return introduced;
    }

    public void setIntroduced(Timestamp introduced) {
	this.introduced = introduced;
    }

    public CompanyEntity getManufacturer() {
	return manufacturer;
    }

    public void setManufacturer(CompanyEntity manufacturer) {
	this.manufacturer = manufacturer;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    @Override
    public int hashCode() {
	return Objects.hash(discontinued, id, introduced, manufacturer, name);
    }

    @Override
    public String toString() {
	return "ComputerEntity [id=" + id + ", name=" + name + ", manufacturer=" + manufacturer + ", introduced="
		+ introduced + ", discontinued=" + discontinued + "]";
    }

    public static class ComputerEntityBuilder {
	private Timestamp discontinued;
	private Long id;
	private Timestamp introduced;
	private CompanyEntity manufacturer;
	private String name;

	ComputerEntityBuilder() {
	}

	public ComputerEntityBuilder discontinued(Timestamp discontinued) {
	    this.discontinued = discontinued;
	    return this;
	}

	public ComputerEntityBuilder id(Long id) {
	    this.id = id;
	    return this;
	}

	public ComputerEntityBuilder introduced(Timestamp introduced) {
	    this.introduced = introduced;
	    return this;
	}

	public ComputerEntityBuilder manufacturer(CompanyEntity manufacturer) {
	    this.manufacturer = manufacturer;
	    return this;
	}

	public ComputerEntityBuilder name(String name) {
	    this.name = name;
	    return this;
	}

	public ComputerEntity build() {
	    return new ComputerEntity(discontinued, id, introduced, manufacturer, name);
	}

	@Override
	public String toString() {
	    return "ComputerEntity.ComputerEntityBuilder(discontinued=" + this.discontinued + ", id=" + this.id
		    + ", introduced=" + this.introduced + ", manufacturer=" + this.manufacturer + ", name=" + this.name
		    + ")";
	}
    }
}
