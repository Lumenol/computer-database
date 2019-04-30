package com.excilys.cdb.exception;

import com.excilys.cdb.validator.Validator;

public class ValidatorException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private final Validator.Result result;

    public ValidatorException(Validator.Result result) {
	super(result.values().toString());
	this.result = result;
    }

    public Validator.Result getResult() {
	return result;
    }
}
