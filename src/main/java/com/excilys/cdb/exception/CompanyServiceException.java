package com.excilys.cdb.exception;

public class CompanyServiceException extends RuntimeException {
    public CompanyServiceException() {
    }

    public CompanyServiceException(String message) {
        super(message);
    }

    public CompanyServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public CompanyServiceException(Throwable cause) {
        super(cause);
    }
}
