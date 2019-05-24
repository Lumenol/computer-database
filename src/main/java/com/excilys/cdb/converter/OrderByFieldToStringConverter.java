package com.excilys.cdb.converter;

import com.excilys.cdb.persistence.page.OrderBy.Field;
import org.springframework.core.convert.converter.Converter;

import java.util.Objects;

public class OrderByFieldToStringConverter implements Converter<Field, String> {
    @Override
    public String convert(Field source) {
        return Objects.toString(source, "");
    }
}
