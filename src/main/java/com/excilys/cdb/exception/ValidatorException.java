package com.excilys.cdb.exception;

import com.excilys.cdb.validator.Result;

public class ValidatorException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private final Result result;

    public ValidatorException(Result result) {
        super(result.getErrors().values().toString());
        this.result = result;
    }

    public Result getResult() {
        return result;
    }
}
