package com.excilys.cdb.web.sorting;

import com.excilys.cdb.shared.pagination.OrderBy;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;
import java.util.function.UnaryOperator;

public class Sorting {

    private final SortingParameters parameters;

    public Sorting(SortingParameters parameters) {
        Objects.requireNonNull(parameters);
        this.parameters = parameters;
    }

    public SortingParameters getParameters() {
        return parameters;
    }

    public void setOrderBy(ModelAndView modelAndView, OrderBy orderBy) {
        final UnaryOperator<String> directionOfFieldName = name -> {
            if (!orderBy.getField().name().equalsIgnoreCase(name) || orderBy.getDirection() == OrderBy.Direction.DESC) {
                return OrderBy.Direction.ASC.getIdentifier();
            } else {
                return OrderBy.Direction.DESC.getIdentifier();
            }
        };
        modelAndView.addObject(replaceDashByUnderscore(parameters.getOrderByUtils()), directionOfFieldName);
        modelAndView.addObject(replaceDashByUnderscore(parameters.getOrderBy()), orderBy.getField().getIdentifier());
        modelAndView.addObject(replaceDashByUnderscore(parameters.getDirection()), orderBy.getDirection().getIdentifier());
    }

    private String replaceDashByUnderscore(String s) {
        return s.replace('-', '_');
    }

}
