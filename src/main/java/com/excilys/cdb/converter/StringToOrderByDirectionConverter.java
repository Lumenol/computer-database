package com.excilys.cdb.converter;

import org.springframework.core.convert.converter.Converter;

import com.excilys.cdb.pagination.OrderBy;

public class StringToOrderByDirectionConverter implements Converter<String, OrderBy.Direction> {
    @Override
    public OrderBy.Direction convert(String source) {
        return OrderBy.Direction.byIdentifier(source).orElseThrow(() -> new IllegalArgumentException(source));
    }
}
