package com.excilys.cdb.model;

import java.util.Objects;

public class Company {
    private Long id;
    private String name;

    public Company() {
    }

    private Company(Long id, String name) {
        Objects.requireNonNull(name);
        this.id = id;
        this.name = name;
    }

    public static CompanyBuilder builder() {
        return new CompanyBuilder();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Company other = (Company) obj;
        return Objects.equals(id, other.id) && Objects.equals(name, other.name);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Compagny [id=" + id + ", name=" + name + "]";
    }

    public static class CompanyBuilder {
        private Long id;
        private String name;

        CompanyBuilder() {
        }

        public CompanyBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public CompanyBuilder name(String name) {
            this.name = name;
            return this;
        }

        public Company build() {
            return new Company(id, name);
        }

        public String toString() {
            return "Company.CompanyBuilder(id=" + this.id + ", name=" + this.name + ")";
        }
    }
}
