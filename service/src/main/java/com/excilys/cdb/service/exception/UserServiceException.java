package com.excilys.cdb.service.exception;

public class UserServiceException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UserServiceException() {
    }

    public UserServiceException(String message) {
        super(message);
    }

    public UserServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserServiceException(Throwable cause) {
        super(cause);
    }
}
