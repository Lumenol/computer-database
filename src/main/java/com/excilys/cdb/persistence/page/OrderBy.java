package com.excilys.cdb.persistence.page;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public class OrderBy {
    private Field field;
    private Meaning meaning;

    OrderBy(Field field, Meaning meaning) {
        this.field = field;
        this.meaning = meaning;
    }

    public static OrderByBuilder builder() {
        return new OrderByBuilder();
    }

    @Override
    public String toString() {
        return "OrderBy{" +
                "field=" + field +
                ", meaning=" + meaning +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderBy orderBy = (OrderBy) o;
        return field == orderBy.field &&
                meaning == orderBy.meaning;
    }

    @Override
    public int hashCode() {
        return Objects.hash(field, meaning);
    }

    public Field getField() {
        return field;
    }

    public Meaning getMeaning() {
        return meaning;
    }

    public enum Field {
        ID("id"), NAME("name"), INTRODUCED("introduced"), DISCONTINUED("discontinued"), COMPANY("company");

        private final String identifier;

        Field(String identifier) {
            this.identifier = identifier;
        }

        public static Optional<Field> byIdentifier(String identifier) {
            return Arrays.stream(Field.values()).filter(field -> field.identifier.equalsIgnoreCase(identifier)).findFirst();
        }

        @Override
        public String toString() {
            return "Field{" +
                    "identifier='" + identifier + '\'' +
                    '}';
        }

        public String getIdentifier() {
            return identifier;
        }
    }

    public enum Meaning {
        ASC("asc"), DESC("desc");

        private final String identifier;

        Meaning(String identifier) {
            this.identifier = identifier;
        }

        public static Optional<Meaning> byIdentifier(String identifier) {
            return Arrays.stream(Meaning.values()).filter(meaning -> meaning.identifier.equalsIgnoreCase(identifier)).findFirst();
        }

        @Override
        public String toString() {
            return "Meaning{" +
                    "identifier='" + identifier + '\'' +
                    '}';
        }

        public String getIdentifier() {
            return identifier;
        }
    }

    public static class OrderByBuilder {
        private Field field = Field.ID;
        private Meaning meaning = Meaning.ASC;

        OrderByBuilder() {
        }

        public OrderByBuilder field(Field field) {
            this.field = field;
            return this;
        }

        public OrderByBuilder meaning(Meaning meaning) {
            this.meaning = meaning;
            return this;
        }

        public OrderBy build() {
            return new OrderBy(field, meaning);
        }

        public String toString() {
            return "OrderBy.OrderByBuilder(field=" + this.field + ", meaning=" + this.meaning + ")";
        }
    }
}
