package com.excilys.cdb.persistence.dto;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Computer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate discontinued;
    private LocalDate introduced;
    @ManyToOne
    @Column(name = "company")
    private Company manufacturer;
    @Column(nullable = false)
    private String name;

    public Computer() {
    }

    Computer(LocalDate discontinued, Long id, LocalDate introduced, Company manufacturer, String name) {
        this.discontinued = discontinued;
        this.id = id;
        this.introduced = introduced;
        this.manufacturer = manufacturer;
        this.name = name;
    }

    public static ComputerBuilder builder() {
        return new ComputerBuilder();
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
        return Objects.equals(discontinued, other.discontinued) && Objects.equals(id, other.id)
                && Objects.equals(introduced, other.introduced) && Objects.equals(manufacturer, other.manufacturer)
                && Objects.equals(name, other.name);
    }

    public LocalDate getDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(LocalDate discontinued) {
        this.discontinued = discontinued;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getIntroduced() {
        return introduced;
    }

    public void setIntroduced(LocalDate introduced) {
        this.introduced = introduced;
    }

    public Company getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Company manufacturer) {
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
        return "Computer [id=" + id + ", name=" + name + ", manufacturer=" + manufacturer + ", introduced=" + introduced
                + ", discontinued=" + discontinued + "]";
    }

    public static class ComputerBuilder {
        private LocalDate discontinued;
        private Long id;
        private LocalDate introduced;
        private Company manufacturer;
        private String name;

        ComputerBuilder() {
        }

        public ComputerBuilder discontinued(LocalDate discontinued) {
            this.discontinued = discontinued;
            return this;
        }

        public ComputerBuilder id(Long id) {
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

        public Computer build() {
            return new Computer(discontinued, id, introduced, manufacturer, name);
        }

        @Override
        public String toString() {
            return "Computer.ComputerBuilder(discontinued=" + this.discontinued + ", id=" + this.id + ", introduced="
                    + this.introduced + ", manufacturer=" + this.manufacturer + ", name=" + this.name + ")";
        }
    }
}
