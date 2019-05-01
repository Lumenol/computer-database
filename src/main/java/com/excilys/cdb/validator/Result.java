package com.excilys.cdb.validator;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

public class Result implements Serializable {

    private static final long serialVersionUID = 1L;
    private final Map<String, String> errors;

    public Result(Map<String, String> errors) {
        this.errors = Collections.unmodifiableMap(errors);
    }

    public boolean isValid() {
        return errors.isEmpty();
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}