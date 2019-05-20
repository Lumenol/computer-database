package com.excilys.cdb.converter;

import java.time.LocalDate;
import java.util.Objects;

import org.springframework.core.convert.converter.Converter;

public class StringToLocalDateConverter implements Converter<String, LocalDate> {
    @Override
    public LocalDate convert(String source) {
	if (Objects.isNull(source) || source.trim().isEmpty()) {
	    return null;
	}
	return LocalDate.parse(source);
    }
}
