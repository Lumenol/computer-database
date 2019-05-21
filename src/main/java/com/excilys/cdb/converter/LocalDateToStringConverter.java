package com.excilys.cdb.converter;

import java.time.LocalDate;
import java.util.Objects;

import org.springframework.core.convert.converter.Converter;

public class LocalDateToStringConverter implements Converter<LocalDate, String> {
    @Override
    public String convert(LocalDate source) {
	return Objects.toString(source, "");
    }
}
