package com.excilys.cdb.converter;

import java.util.Objects;

import org.springframework.core.convert.converter.Converter;

import com.excilys.cdb.persistence.page.OrderBy.Field;

public class OrderByFieldToStringConverter implements Converter<Field, String> {
    @Override
    public String convert(Field source) {
	return Objects.toString(source, "");
    }
}
