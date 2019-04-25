package com.excilys.cdb.validator;

import java.util.Objects;

class ValidatorUtils {
    static boolean isBlank(String s) {
	return Objects.isNull(s) || s.trim().isEmpty();
    }

    private ValidatorUtils() {
    }
}
