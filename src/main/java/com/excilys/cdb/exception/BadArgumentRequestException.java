package com.excilys.cdb.exception;

public class BadArgumentRequestException extends RuntimeException {
    public BadArgumentRequestException() {
    }

    public BadArgumentRequestException(String message) {
        super(message);
    }

    public BadArgumentRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadArgumentRequestException(Throwable cause) {
        super(cause);
    }
}
