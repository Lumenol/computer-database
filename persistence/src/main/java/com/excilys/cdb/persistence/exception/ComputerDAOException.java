package com.excilys.cdb.persistence.exception;

public class ComputerDAOException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ComputerDAOException() {
    }

    public ComputerDAOException(String message) {
        super(message);
    }

    public ComputerDAOException(String message, Throwable cause) {
        super(message, cause);
    }

    public ComputerDAOException(Throwable cause) {
        super(cause);
    }
}
