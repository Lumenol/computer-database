package com.excilys.cdb.converter;

import org.springframework.core.convert.converter.Converter;

import com.excilys.cdb.pagination.OrderBy.Direction;

import java.util.Objects;

public class OrderByDirectionToStringConverter implements Converter<Direction, String> {
    @Override
    public String convert(Direction source) {
        return Objects.toString(source, "");
    }
}
