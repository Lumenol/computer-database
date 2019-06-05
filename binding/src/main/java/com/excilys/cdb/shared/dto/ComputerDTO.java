package com.excilys.cdb.shared.dto;

import java.time.LocalDate;
import java.util.Objects;

public class ComputerDTO {
    private LocalDate discontinued;
    private long id;
    private LocalDate introduced;
    private String manufacturer;
    private String name;

    public ComputerDTO() {
    }

    ComputerDTO(LocalDate discontinued, long id, LocalDate introduced, String manufacturer, String name) {
        this.discontinued = discontinued;
        this.id = id;
        this.introduced = introduced;
        this.manufacturer = manufacturer;
        this.name = name;
    }

    public static ComputerDTOBuilder builder() {
        return new ComputerDTOBuilder();
    }

    @Override
    public String toString() {
        return "ComputerDTO{" + "discontinued='" + discontinued + '\'' + ", id=" + id + ", introduced='" + introduced
                + '\'' + ", manufacturer='" + manufacturer + '\'' + ", name='" + name + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ComputerDTO that = (ComputerDTO) o;
        return id == that.id && Objects.equals(discontinued, that.discontinued)
                && Objects.equals(introduced, that.introduced) && Objects.equals(manufacturer, that.manufacturer)
                && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(discontinued, id, introduced, manufacturer, name);
    }

    public LocalDate getDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(LocalDate discontinued) {
        this.discontinued = discontinued;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getIntroduced() {
        return introduced;
    }

    public void setIntroduced(LocalDate introduced) {
        this.introduced = introduced;
    }

    public String getmanufacturer() {
        return manufacturer;
    }

    public void setmanufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static class ComputerDTOBuilder {
        private LocalDate discontinued;
        private long id;
        private LocalDate introduced;
        private String manufacturer;
        private String name;

        ComputerDTOBuilder() {
        }

        public ComputerDTOBuilder discontinued(LocalDate discontinued) {
            this.discontinued = discontinued;
            return this;
        }

        public ComputerDTOBuilder id(long id) {
            this.id = id;
            return this;
        }

        public ComputerDTOBuilder introduced(LocalDate introduced) {
            this.introduced = introduced;
            return this;
        }

        public ComputerDTOBuilder manufacturer(String manufacturer) {
            this.manufacturer = manufacturer;
            return this;
        }

        public ComputerDTOBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ComputerDTO build() {
            return new ComputerDTO(discontinued, id, introduced, manufacturer, name);
        }

        @Override
        public String toString() {
            return "ComputerDTO.ComputerDTOBuilder(discontinued=" + this.discontinued + ", id=" + this.id
                    + ", introduced=" + this.introduced + ", manufacturer=" + this.manufacturer + ", name="
                    + this.name + ")";
        }
    }
}
