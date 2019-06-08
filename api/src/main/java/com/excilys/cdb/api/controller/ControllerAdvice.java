package com.excilys.cdb.api.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

@RestControllerAdvice
public class ControllerAdvice extends ResponseEntityExceptionHandler {

    private static final String REASON = "reason";
    private static final String STATUS = "status";

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> defaultHandleException(Exception ex, WebRequest request) {
        final HashMap<String, Object> body = new HashMap<>();
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        body.put(REASON, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());

        final Optional<ResponseStatus> responseStatuts = Optional.ofNullable(ex).map(Object::getClass)
                .map(c -> c.getAnnotation(ResponseStatus.class));
        if (responseStatuts.isPresent()) {
            final ResponseStatus responseStatus = responseStatuts.get();
            status = responseStatus.code();
            body.put(REASON, responseStatus.reason().isEmpty() ? responseStatus.code().getReasonPhrase()
                    : responseStatus.reason());
        }
        body.put(STATUS, status.value());
        return handleExceptionInternal(ex, body, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (Objects.isNull(body)) {
            final HashMap<String, Object> map = new HashMap<>();
            map.put(REASON, status.getReasonPhrase());
            map.put(STATUS, status.value());
            body = map;
        }
        return super.handleExceptionInternal(ex, body, headers, status, request);
    }
}
