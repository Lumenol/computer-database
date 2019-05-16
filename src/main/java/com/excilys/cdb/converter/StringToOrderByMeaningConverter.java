package com.excilys.cdb.converter;

import com.excilys.cdb.persistence.page.OrderBy;
import org.springframework.core.convert.converter.Converter;

public class StringToOrderByMeaningConverter implements Converter<String, OrderBy.Meaning> {
    @Override
    public OrderBy.Meaning convert(String source) {
        return OrderBy.Meaning.byIdentifier(source).orElseThrow(() -> new IllegalArgumentException(source));
    }
}
