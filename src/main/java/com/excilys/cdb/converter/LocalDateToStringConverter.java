package com.excilys.cdb.converter;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.util.Objects;

public class LocalDateToStringConverter implements Converter<LocalDate, String> {
    @Override
    public String convert(LocalDate source) {
        return Objects.toString(source, "");
    }
}
