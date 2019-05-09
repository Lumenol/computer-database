package com.excilys.cdb.servlet.sorting;

import java.util.Objects;

public class SortingParameters {

    private static final String PARAMETER_ORDER_BY = "order-by";
    private static final String PARAMETER_MEANING = "meaning";
    private static final String PARAMETER_ORDER_BY_UTILS = "order-utils";

    static final SortingParameters DEFAULT_SORTING_PARAMETERS = new SortingParameters(PARAMETER_ORDER_BY,
	    PARAMETER_MEANING, PARAMETER_ORDER_BY_UTILS);

    private final String orderBy;
    private final String meaning;
    private final String orderByUtils;

    public SortingParameters(String orderBy, String meaning, String orderByUtils) {
	Objects.requireNonNull(orderBy);
	Objects.requireNonNull(meaning);
	Objects.requireNonNull(orderByUtils);

	this.orderBy = orderBy;
	this.meaning = meaning;
	this.orderByUtils = orderByUtils;
    }

    public String getOrderByUtils() {
	return orderByUtils;
    }

    public String getOrderBy() {
	return orderBy;
    }

    public String getMeaning() {
	return meaning;
    }

    @Override
    public String toString() {
	return "SortingParameters [orderBy=" + orderBy + ", meaning=" + meaning + ", orderByUtils=" + orderByUtils
		+ "]";
    }

    @Override
    public int hashCode() {
	return Objects.hash(meaning, orderBy, orderByUtils);
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
	return Objects.equals(meaning, other.meaning) && Objects.equals(orderBy, other.orderBy)
		&& Objects.equals(orderByUtils, other.orderByUtils);
    }

}
