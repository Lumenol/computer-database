package com.excilys.cdb.webapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
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
