package com.excilys.cdb.shared.exception;

public class ConstructorWithCauseNotFound extends RuntimeException {
    public ConstructorWithCauseNotFound() {
    }

    public ConstructorWithCauseNotFound(String message) {
        super(message);
    }

    public ConstructorWithCauseNotFound(String message, Throwable cause) {
        super(message, cause);
    }

    public ConstructorWithCauseNotFound(Throwable cause) {
        super(cause);
    }
}
