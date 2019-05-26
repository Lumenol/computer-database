package com.excilys.cdb.shared.dto;

import java.time.LocalDate;
import java.util.Objects;

public class UpdateComputerDTO {
    private Long id;
    private LocalDate discontinued;
    private LocalDate introduced;
    private Long mannufacturerId;
    private String name;

    public UpdateComputerDTO() {
    }

    UpdateComputerDTO(Long id, LocalDate discontinued, LocalDate introduced, Long mannufacturerId, String name) {
        this.id = id;
        this.discontinued = discontinued;
        this.introduced = introduced;
        this.mannufacturerId = mannufacturerId;
        this.name = name;
    }

    public static UpdateComputerDTOBuilder builder() {
        return new UpdateComputerDTOBuilder();
    }

    @Override
    public String toString() {
        return "UpdateComputerDTO{" + "id='" + id + '\'' + ", discontinued='" + discontinued + '\'' + ", introduced='"
                + introduced + '\'' + ", mannufacturerId='" + mannufacturerId + '\'' + ", name='" + name + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UpdateComputerDTO that = (UpdateComputerDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(discontinued, that.discontinued)
                && Objects.equals(introduced, that.introduced) && Objects.equals(mannufacturerId, that.mannufacturerId)
                && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, discontinued, introduced, mannufacturerId, name);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public static class UpdateComputerDTOBuilder {
        private Long id;
        private LocalDate discontinued;
        private LocalDate introduced;
        private Long mannufacturerId;
        private String name;

        UpdateComputerDTOBuilder() {
        }

        public UpdateComputerDTOBuilder id(Long string) {
            this.id = string;
            return this;
        }

        @Override
        public int hashCode() {
            return Objects.hash(discontinued, id, introduced, mannufacturerId, name);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            UpdateComputerDTOBuilder other = (UpdateComputerDTOBuilder) obj;
            return Objects.equals(discontinued, other.discontinued) && id == other.id
                    && Objects.equals(introduced, other.introduced)
                    && Objects.equals(mannufacturerId, other.mannufacturerId) && Objects.equals(name, other.name);
        }

        public UpdateComputerDTOBuilder discontinued(LocalDate discontinued) {
            this.discontinued = discontinued;
            return this;
        }

        public UpdateComputerDTOBuilder introduced(LocalDate introduced) {
            this.introduced = introduced;
            return this;
        }

        public UpdateComputerDTOBuilder mannufacturerId(Long mannufacturerId) {
            this.mannufacturerId = mannufacturerId;
            return this;
        }

        public UpdateComputerDTOBuilder name(String name) {
            this.name = name;
            return this;
        }

        public UpdateComputerDTO build() {
            return new UpdateComputerDTO(id, discontinued, introduced, mannufacturerId, name);
        }

        @Override
        public String toString() {
            return "UpdateComputerDTO.UpdateComputerDTOBuilder(id=" + this.id + ", discontinued=" + this.discontinued
                    + ", introduced=" + this.introduced + ", mannufacturerId=" + this.mannufacturerId + ", name="
                    + this.name + ")";
        }
    }
}
