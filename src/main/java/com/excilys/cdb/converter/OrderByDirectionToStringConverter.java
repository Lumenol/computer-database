package com.excilys.cdb.converter;

import java.util.Objects;

import org.springframework.core.convert.converter.Converter;

import com.excilys.cdb.persistence.page.OrderBy.Direction;

public class OrderByDirectionToStringConverter implements Converter<Direction, String> {
    @Override
    public String convert(Direction source) {
	return Objects.toString(source, "");
    }
}
