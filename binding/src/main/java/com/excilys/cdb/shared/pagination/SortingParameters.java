package com.excilys.cdb.shared.pagination;


import java.util.Objects;

public class SortingParameters {

    private final String orderBy;
    private final String direction;
    private final String orderByUtils;

    public SortingParameters(String orderBy, String direction, String orderByUtils) {
        Objects.requireNonNull(orderBy);
        Objects.requireNonNull(direction);
        Objects.requireNonNull(orderByUtils);

        this.orderBy = orderBy;
        this.direction = direction;
        this.orderByUtils = orderByUtils;
    }

    @Override
    public String toString() {
        return "SortingParameters{" +
                "orderBy='" + orderBy + '\'' +
                ", direction='" + direction + '\'' +
                ", orderByUtils='" + orderByUtils + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SortingParameters that = (SortingParameters) o;
        return Objects.equals(orderBy, that.orderBy) &&
                Objects.equals(direction, that.direction) &&
                Objects.equals(orderByUtils, that.orderByUtils);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderBy, direction, orderByUtils);
    }

    public String getOrderByUtils() {
        return orderByUtils;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public String getDirection() {
        return direction;
    }

}
