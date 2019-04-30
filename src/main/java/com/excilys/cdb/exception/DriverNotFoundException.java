package com.excilys.cdb.exception;

public class DriverNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public DriverNotFoundException() {
    }

    public DriverNotFoundException(String message) {
	super(message);
    }

    public DriverNotFoundException(String message, Throwable cause) {
	super(message, cause);
    }

    public DriverNotFoundException(Throwable cause) {
	super(cause);
    }
}
