package com.excilys.cdb.converter;

import org.springframework.core.convert.converter.Converter;

import com.excilys.cdb.pagination.OrderBy;

public class StringToOrderByFieldConverter implements Converter<String, OrderBy.Field> {
    @Override
    public OrderBy.Field convert(String source) {
        return OrderBy.Field.byIdentifier(source).orElseThrow(() -> new IllegalArgumentException(source));
    }
}
