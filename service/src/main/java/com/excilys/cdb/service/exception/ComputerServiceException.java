package com.excilys.cdb.service.exception;

public class ComputerServiceException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ComputerServiceException() {
    }

    public ComputerServiceException(String message) {
        super(message);
    }

    public ComputerServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ComputerServiceException(Throwable cause) {
        super(cause);
    }
}
