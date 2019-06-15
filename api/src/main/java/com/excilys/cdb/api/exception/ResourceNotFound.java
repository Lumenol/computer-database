package com.excilys.cdb.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ResourceNotFound extends RuntimeException {

    private static final long serialVersionUID = 948674961876894912L;

    public ResourceNotFound() {
        super();
    }

    public ResourceNotFound(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public ResourceNotFound(String arg0) {
        super(arg0);
    }

    public ResourceNotFound(Throwable arg0) {
        super(arg0);
    }

}
