package com.excilys.cdb.exception;

public class ComputerServiceException extends RuntimeException {
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
