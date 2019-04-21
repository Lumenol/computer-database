package com.excilys.cdb.model;

import java.util.Objects;

public class Company {

    private final Long id;
    private final String name;

    private Company(Long id, String name) {
        Objects.requireNonNull(name);
        this.id = id;
        this.name = name;
    }

    public static CompagnyBuilder builder() {
        return new CompagnyBuilder();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Compagny [id=" + id + ", name=" + name + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return Objects.equals(id, company.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static class CompagnyBuilder {

        private Long id;

        private String name;

        private CompagnyBuilder() {
        }

        public Company build() {
            return new Company(id, name);
        }

        public CompagnyBuilder id(long id) {
            this.id = id;
            return this;
        }

        public CompagnyBuilder name(String name) {
            this.name = name;
            return this;
        }

    }

}
