package com.excilys.cdb.shared.dto;

import java.time.LocalDate;
import java.util.Objects;

public class CreateComputerDTO {
    private LocalDate discontinued;
    private LocalDate introduced;
    private Long mannufacturerId;
    private String name;

    CreateComputerDTO(LocalDate discontinued, LocalDate introduced, Long mannufacturerId, String name) {
        this.discontinued = discontinued;
        this.introduced = introduced;
        this.mannufacturerId = mannufacturerId;
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
                + ", mannufacturerId='" + mannufacturerId + '\'' + ", name='" + name + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        CreateComputerDTO that = (CreateComputerDTO) o;
        return Objects.equals(discontinued, that.discontinued) && Objects.equals(introduced, that.introduced)
                && Objects.equals(mannufacturerId, that.mannufacturerId) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(discontinued, introduced, mannufacturerId, name);
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

    public Long getMannufacturerId() {
        return mannufacturerId;
    }

    public void setMannufacturerId(Long mannufacturerId) {
        this.mannufacturerId = mannufacturerId;
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
        private Long mannufacturerId;
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

        public CreateComputerDTOBuilder mannufacturerId(Long mannufacturerId) {
            this.mannufacturerId = mannufacturerId;
            return this;
        }

        public CreateComputerDTOBuilder name(String name) {
            this.name = name;
            return this;
        }

        public CreateComputerDTO build() {
            return new CreateComputerDTO(discontinued, introduced, mannufacturerId, name);
        }

        @Override
        public String toString() {
            return "CreateComputerDTO.CreateComputerDTOBuilder(discontinued=" + this.discontinued + ", introduced="
                    + this.introduced + ", mannufacturerId=" + this.mannufacturerId + ", name=" + this.name + ")";
        }
    }
}
