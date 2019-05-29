package com.excilys.cdb.shared.dto;

import java.time.LocalDate;
import java.util.Objects;

public class CreateComputerDTO {
    private LocalDate discontinued;
    private LocalDate introduced;
    private Long manufacturerId;
    private String name;

    CreateComputerDTO(LocalDate discontinued, LocalDate introduced, Long manufacturerId, String name) {
        this.discontinued = discontinued;
        this.introduced = introduced;
        this.manufacturerId = manufacturerId;
        this.name = name;
    }

    public CreateComputerDTO() {
    }

    public static CreateComputerDTOBuilder builder() {
        return new CreateComputerDTOBuilder();
    }

    @Override
    public String toString() {
        return "CreateComputerDTO{" + "discontinued='" + discontinued + '\'' + ", introduced='" + introduced + '\''
                + ", manufacturerId='" + manufacturerId + '\'' + ", name='" + name + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        CreateComputerDTO that = (CreateComputerDTO) o;
        return Objects.equals(discontinued, that.discontinued) && Objects.equals(introduced, that.introduced)
                && Objects.equals(manufacturerId, that.manufacturerId) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(discontinued, introduced, manufacturerId, name);
    }

    public LocalDate getDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(LocalDate discontinued) {
        this.discontinued = discontinued;
    }

    public LocalDate getIntroduced() {
        return introduced;
    }

    public void setIntroduced(LocalDate introduced) {
        this.introduced = introduced;
    }

    public Long getmanufacturerId() {
        return manufacturerId;
    }

    public void setmanufacturerId(Long manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static class CreateComputerDTOBuilder {
        private LocalDate discontinued;
        private LocalDate introduced;
        private Long manufacturerId;
        private String name;

        CreateComputerDTOBuilder() {
        }

        public CreateComputerDTOBuilder discontinued(LocalDate discontinued) {
            this.discontinued = discontinued;
            return this;
        }

        public CreateComputerDTOBuilder introduced(LocalDate introduced) {
            this.introduced = introduced;
            return this;
        }

        public CreateComputerDTOBuilder manufacturerId(Long manufacturerId) {
            this.manufacturerId = manufacturerId;
            return this;
        }

        public CreateComputerDTOBuilder name(String name) {
            this.name = name;
            return this;
        }

        public CreateComputerDTO build() {
            return new CreateComputerDTO(discontinued, introduced, manufacturerId, name);
        }

        @Override
        public String toString() {
            return "CreateComputerDTO.CreateComputerDTOBuilder(discontinued=" + this.discontinued + ", introduced="
                    + this.introduced + ", manufacturerId=" + this.manufacturerId + ", name=" + this.name + ")";
        }
    }
}
