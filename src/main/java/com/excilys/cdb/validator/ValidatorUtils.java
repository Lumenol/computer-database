package com.excilys.cdb.validator;

import java.util.Objects;

final class ValidatorUtils {

    private ValidatorUtils() {
    }

    static boolean isBlank(String s) {
	return Objects.isNull(s) || s.trim().isEmpty();
    }
}
