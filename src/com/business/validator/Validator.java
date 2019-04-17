package com.metier.validator;

import java.util.Map;

public interface Validator<T> {
	default boolean isValid() {
		return errors().isEmpty();
	}

	Map<String, String> errors();
}
