package com.excilys.cdb.exception;

public class MapperException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public MapperException() {
    }

    public MapperException(String message) {
	super(message);
    }

    public MapperException(String message, Throwable cause) {
	super(message, cause);
    }

    public MapperException(Throwable cause) {
	super(cause);
    }

}
