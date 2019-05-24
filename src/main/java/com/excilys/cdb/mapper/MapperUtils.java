package com.excilys.cdb.mapper;

import java.time.LocalDate;
import java.util.Objects;

public final class MapperUtils {
    private MapperUtils() {
    }

    public static LocalDate parseDate(String date) {
        if (isBlank(date)) {
            return null;
        } else {
            return LocalDate.parse(date);
        }
    }

    public static Long parseId(String id) {
        if (isBlank(id)) {
            return null;
        } else {
            return Long.valueOf(id);
        }
    }

    private static boolean isBlank(String s) {
        return Objects.isNull(s) || s.trim().isEmpty();
    }

}
