package com.excilys.cdb.validator;

import java.util.Objects;

class ValidatorUtils {
    private ValidatorUtils() {
    }

    static boolean isBlank(String s) {
	return Objects.isNull(s) || s.trim().isEmpty();
    }
}
