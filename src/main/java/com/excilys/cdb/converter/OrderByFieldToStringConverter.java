package com.excilys.cdb.converter;

import org.springframework.core.convert.converter.Converter;

import com.excilys.cdb.pagination.OrderBy.Field;

import java.util.Objects;

public class OrderByFieldToStringConverter implements Converter<Field, String> {
    @Override
    public String convert(Field source) {
        return Objects.toString(source, "");
    }
}
