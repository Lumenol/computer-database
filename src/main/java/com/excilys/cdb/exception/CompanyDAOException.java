package com.excilys.cdb.exception;

public class CompanyDAOException extends RuntimeException {
    public CompanyDAOException() {
    }

    public CompanyDAOException(String message) {
        super(message);
    }

    public CompanyDAOException(String message, Throwable cause) {
        super(message, cause);
    }

    public CompanyDAOException(Throwable cause) {
        super(cause);
    }
}
