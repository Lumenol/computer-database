package com.excilys.cdb.model;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

public class Computer {

    private final Optional<LocalDate> discontinued;
    private final Long id;
    private final Optional<LocalDate> introduced;
    private final Optional<Company> manufacturer;
    private final String name;

    private Computer(Long id, String name, Optional<Company> manufacturer, Optional<LocalDate> introduced,
                     Optional<LocalDate> discontinued) {
        super();
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Computer computer = (Computer) o;
        return Objects.equals(id, computer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Optional<LocalDate> getIntroduced() {
        return introduced;
    }

    public Optional<Company> getManufacturer() {
        return manufacturer;
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

}
