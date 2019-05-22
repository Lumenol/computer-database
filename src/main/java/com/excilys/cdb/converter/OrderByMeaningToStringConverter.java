package com.excilys.cdb.converter;

import java.util.Objects;

import org.springframework.core.convert.converter.Converter;

import com.excilys.cdb.persistence.page.OrderBy.Meaning;

public class OrderByMeaningToStringConverter implements Converter<Meaning, String> {
    @Override
    public String convert(Meaning source) {
	return Objects.toString(source, "");
    }
}
