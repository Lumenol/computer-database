package com.excilys.cdb.converter;

import com.excilys.cdb.persistence.page.OrderBy.Direction;
import org.springframework.core.convert.converter.Converter;

import java.util.Objects;

public class OrderByDirectionToStringConverter implements Converter<Direction, String> {
    @Override
    public String convert(Direction source) {
        return Objects.toString(source, "");
    }
}
