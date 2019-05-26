package com.excilys.cdb.persistence.exception;

public class CompanyDAOException extends RuntimeException {
    private static final long serialVersionUID = 1L;

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
