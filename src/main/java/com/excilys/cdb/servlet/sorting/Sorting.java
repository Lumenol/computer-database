package com.excilys.cdb.servlet.sorting;

import java.util.Objects;
import java.util.function.UnaryOperator;

import javax.servlet.http.HttpServletRequest;

import com.excilys.cdb.persistence.page.OrderBy;

public class Sorting {

    private final SortingParameters parameters;

    public Sorting(SortingParameters parameters) {
	Objects.requireNonNull(parameters);
	this.parameters = parameters;
    }

    public SortingParameters getParameters() {
	return parameters;
    }

    public OrderBy getOrderBy(HttpServletRequest request) {
	final String field = request.getParameter(parameters.getOrderBy());
	final String meaning = request.getParameter(parameters.getMeaning());

	final OrderBy.OrderByBuilder builder = OrderBy.builder();
	builder.field(OrderBy.Field.byIdentifier(field).orElse(OrderBy.Field.NAME));
	OrderBy.Meaning.byIdentifier(meaning).ifPresent(builder::meaning);

	return builder.build();
    }

    public void setOrderBy(HttpServletRequest request, OrderBy orderBy) {
	final UnaryOperator<String> meaningOfFieldName = name -> {
	    if (!orderBy.getField().name().equalsIgnoreCase(name) || orderBy.getMeaning() == OrderBy.Meaning.DESC) {
		return OrderBy.Meaning.ASC.getIdentifier();
	    } else {
		return OrderBy.Meaning.DESC.getIdentifier();
	    }
	};
	request.setAttribute(replaceDashByUnderscore(parameters.getOrderByUtils()), meaningOfFieldName);
	request.setAttribute(replaceDashByUnderscore(parameters.getOrderBy()), orderBy.getField().getIdentifier());
	request.setAttribute(replaceDashByUnderscore(parameters.getMeaning()), orderBy.getMeaning().getIdentifier());
    }

    private String replaceDashByUnderscore(String s) {
	return s.replace('-', '_');
    }

}
