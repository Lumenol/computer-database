package com.excilys.cdb.web.converter;

import com.excilys.cdb.shared.pagination.OrderBy.Direction;
import org.springframework.core.convert.converter.Converter;

import java.util.Objects;

public class OrderByDirectionToStringConverter implements Converter<Direction, String> {
    @Override
    public String convert(Direction source) {
        return Objects.toString(source, "");
    }
}
