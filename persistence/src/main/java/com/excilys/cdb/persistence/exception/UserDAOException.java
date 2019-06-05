package com.excilys.cdb.persistence.exception;

public class UserDAOException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UserDAOException() {
    }

    public UserDAOException(String message) {
        super(message);
    }

    public UserDAOException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserDAOException(Throwable cause) {
        super(cause);
    }
}
