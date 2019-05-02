package com.excilys.cdb.dto;

import java.util.Objects;

public class CompanyDTO {
    private long id;
    private String name;

    public CompanyDTO() {
    }

    CompanyDTO(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static CompanyDTOBuilder builder() {
        return new CompanyDTOBuilder();
    }

    @Override
    public String toString() {
        return "CompanyDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompanyDTO that = (CompanyDTO) o;
        return id == that.id &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    public static class CompanyDTOBuilder {
        private long id;
        private String name;

        CompanyDTOBuilder() {
        }

        public CompanyDTOBuilder id(long id) {
            this.id = id;
            return this;
        }

        public CompanyDTOBuilder name(String name) {
            this.name = name;
            return this;
        }

        public CompanyDTO build() {
            return new CompanyDTO(id, name);
        }

        public String toString() {
            return "CompanyDTO.CompanyDTOBuilder(id=" + this.id + ", name=" + this.name + ")";
        }
    }
}
