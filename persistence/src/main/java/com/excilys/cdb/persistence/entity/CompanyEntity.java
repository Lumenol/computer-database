package com.excilys.cdb.persistence.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "company")
public class CompanyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;

    public CompanyEntity() {
    }

    private CompanyEntity(Long id, String name) {
        Objects.requireNonNull(name);
        this.id = id;
        this.name = name;
    }

    public static CompanyEntityBuilder builder() {
        return new CompanyEntityBuilder();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CompanyEntity other = (CompanyEntity) obj;
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
        return "CompanyEntity [id=" + id + ", name=" + name + "]";
    }

    public static class CompanyEntityBuilder {
        private Long id;
        private String name;

        CompanyEntityBuilder() {
        }

        public CompanyEntityBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public CompanyEntityBuilder name(String name) {
            this.name = name;
            return this;
        }

        public CompanyEntity build() {
            return new CompanyEntity(id, name);
        }

        public String toString() {
            return "CompanyEntity.CompanyEntityBuilder(id=" + this.id + ", name=" + this.name + ")";
        }
    }
}
