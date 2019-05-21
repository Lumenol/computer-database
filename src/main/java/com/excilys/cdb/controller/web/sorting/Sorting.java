package com.excilys.cdb.controller.web.sorting;

import com.excilys.cdb.persistence.page.OrderBy;
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
        final UnaryOperator<String> meaningOfFieldName = name -> {
            if (!orderBy.getField().name().equalsIgnoreCase(name) || orderBy.getMeaning() == OrderBy.Meaning.DESC) {
                return OrderBy.Meaning.ASC.getIdentifier();
            } else {
                return OrderBy.Meaning.DESC.getIdentifier();
            }
        };
        modelAndView.addObject(replaceDashByUnderscore(parameters.getOrderByUtils()), meaningOfFieldName);
        modelAndView.addObject(replaceDashByUnderscore(parameters.getOrderBy()), orderBy.getField().getIdentifier());
        modelAndView.addObject(replaceDashByUnderscore(parameters.getMeaning()), orderBy.getMeaning().getIdentifier());
    }

    private String replaceDashByUnderscore(String s) {
        return s.replace('-', '_');
    }

}