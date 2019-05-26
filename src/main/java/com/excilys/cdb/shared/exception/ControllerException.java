package com.excilys.cdb.shared.exception;

public class ControllerException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ControllerException() {
    }

    public ControllerException(String message) {
        super(message);
    }
}
