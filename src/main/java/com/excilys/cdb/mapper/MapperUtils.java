package com.excilys.cdb.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Objects;

import com.excilys.cdb.exception.ValidationException;

public final class MapperUtils {
    private MapperUtils() {
    }

    public static LocalDate parseDate(String field, String date) {
	try {
	    return parseDate(date);
	} catch (DateTimeParseException e) {
	    throw new ValidationException(field, "La date est mal écrit.");
	}
    }

    public static LocalDate parseDate(String date) {
	if (isBlank(date)) {
	    return null;
	} else {
	    return LocalDate.parse(date);
	}
    }

    public static Long parseId(String field, String l) {
	try {
	    return parseLong(l);
	} catch (DateTimeParseException e) {
	    throw new ValidationException(field, "L'id est mal écrit.");
	}
    }

    public static Long parseLong(String l) {
	if (isBlank(l)) {
	    return null;
	} else {
	    return Long.valueOf(l);
	}
    }

    private static boolean isBlank(String s) {
	return Objects.isNull(s) || s.trim().isEmpty();
    }

}
