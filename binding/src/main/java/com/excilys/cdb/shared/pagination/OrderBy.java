package com.excilys.cdb.shared.pagination;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public class OrderBy {
    private Field field;
    private Direction direction;

    public OrderBy() {
        this(Field.NAME, Direction.ASC);
    }

    OrderBy(Field field, Direction direction) {
        this.field = field;
        this.direction = direction;
    }

    public static OrderByBuilder builder() {
        return new OrderByBuilder();
    }

    @Override
    public String toString() {
        return "OrderBy{" + "field=" + field + ", direction=" + direction + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        OrderBy orderBy = (OrderBy) o;
        return field == orderBy.field && direction == orderBy.direction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(field, direction);
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public enum Field {
        ID("id"), NAME("name"), INTRODUCED("introduced"), DISCONTINUED("discontinued"), COMPANY("company");

        private final String identifier;

        Field(String identifier) {
            this.identifier = identifier;
        }

        public static Optional<Field> byIdentifier(String identifier) {
            return Arrays.stream(Field.values()).filter(field -> field.identifier.equalsIgnoreCase(identifier))
                    .findFirst();
        }

        @Override
        public String toString() {
            return "Field{" + "identifier='" + identifier + '\'' + '}';
        }

        public String getIdentifier() {
            return identifier;
        }
    }

    public enum Direction {
        ASC("asc"), DESC("desc");

        private final String identifier;

        Direction(String identifier) {
            this.identifier = identifier;
        }

        public static Optional<Direction> byIdentifier(String identifier) {
            return Arrays.stream(Direction.values()).filter(direction -> direction.identifier.equalsIgnoreCase(identifier))
                    .findFirst();
        }

        @Override
        public String toString() {
            return "Direction{" + "identifier='" + identifier + '\'' + '}';
        }

        public String getIdentifier() {
            return identifier;
        }
    }

    public static class OrderByBuilder {
        private Field field = Field.ID;
        private Direction direction = Direction.ASC;

        OrderByBuilder() {
        }

        public OrderByBuilder field(Field field) {
            this.field = field;
            return this;
        }

        public OrderByBuilder direction(Direction direction) {
            this.direction = direction;
            return this;
        }

        public OrderBy build() {
            return new OrderBy(field, direction);
        }

        @Override
        public String toString() {
            return "OrderBy.OrderByBuilder(field=" + this.field + ", direction=" + this.direction + ")";
        }
    }
}
