package com.business.validator;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractValidator<T> implements Validator<T> {
    private Map<String, String> errors;

    public AbstractValidator(T t) {
	errors = check(t);
    }

    private Map<String, String> check(T t) {
	Map<String, String> errors = new HashMap<>();
	check(t, errors);
	return Collections.unmodifiableMap(errors);
    }

    /**
     * 
     * @param t      object à contrôler
     * @param errors map pour enregister les erreurs
     */
    protected abstract void check(T t, Map<String, String> errors);

    @Override
    public final Map<String, String> errors() {
	return errors;
    }
}
