package com.business.validator;

import java.util.Map;

public interface Validator<T> {
    Map<String, String> errors();

    default boolean isValid() {
	return errors().isEmpty();
    }
}
