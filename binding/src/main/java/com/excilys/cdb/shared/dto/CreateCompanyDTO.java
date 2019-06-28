package com.excilys.cdb.shared.dto;

import java.util.Objects;

public class CreateCompanyDTO {
    private String name;

    public CreateCompanyDTO() {
    }

    private CreateCompanyDTO(String name) {
        this.name = name;
    }

    public static CreateCompanyDTOBuilder builder() {
        return new CreateCompanyDTOBuilder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateCompanyDTO that = (CreateCompanyDTO) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static class CreateCompanyDTOBuilder {
        private String name;

        CreateCompanyDTOBuilder() {
        }

        public CreateCompanyDTOBuilder name(String name) {
            this.name = name;
            return this;
        }

        @Override
        public String toString() {
            return "CreateCompanyDTOBuilder{" +
                    "name='" + name + '\'' +
                    '}';
        }

        public CreateCompanyDTO build() {
            return new CreateCompanyDTO(name);
        }

    }
}
