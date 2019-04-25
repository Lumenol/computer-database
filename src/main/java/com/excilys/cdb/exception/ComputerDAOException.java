package com.excilys.cdb.exception;

public class ComputerDAOException extends RuntimeException {
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
