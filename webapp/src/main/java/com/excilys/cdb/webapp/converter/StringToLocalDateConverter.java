package com.excilys.cdb.webapp.converter;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.util.Objects;

public class StringToLocalDateConverter implements Converter<String, LocalDate> {
    @Override
    public LocalDate convert(String source) {
        if (Objects.isNull(source) || source.trim().isEmpty()) {
            return null;
        }
        return LocalDate.parse(source);
    }
}
