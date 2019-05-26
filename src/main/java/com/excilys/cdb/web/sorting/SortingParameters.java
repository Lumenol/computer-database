package com.excilys.cdb.web.sorting;

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

    public String getOrderByUtils() {
        return orderByUtils;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public String getDirection() {
        return direction;
    }

    @Override
    public String toString() {
        return "SortingParameters [orderBy=" + orderBy + ", direction=" + direction + ", orderByUtils=" + orderByUtils
                + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(direction, orderBy, orderByUtils);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SortingParameters other = (SortingParameters) obj;
        return Objects.equals(direction, other.direction) && Objects.equals(orderBy, other.orderBy)
                && Objects.equals(orderByUtils, other.orderByUtils);
    }

}
