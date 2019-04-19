package com.excilys.cdb.validator;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

public abstract class AbstractValidator<T> {

    public static class Result {

	private Map<String, String> errors;

	private Result(Map<String, String> errors) {
	    this.errors = errors;
	}

	protected String put(String key, String value) {
	    return errors.put(key, value);
	}

	public boolean containsKey(Object arg0) {
	    return errors.containsKey(arg0);
	}

	public void forEach(BiConsumer<? super String, ? super String> arg0) {
	    errors.forEach(arg0);
	}

	public String get(Object arg0) {
	    return errors.get(arg0);
	}

	public Set<String> keySet() {
	    return errors.keySet();
	}

	public Collection<String> values() {
	    return errors.values();
	}

	boolean isValid() {
	    return errors.isEmpty();
	}
    }

    public Result check(T toValidate) {
	return new Result(validation(toValidate));
    }

    protected abstract Map<String, String> validation(T toValidate);

}
