package com.excilys.cdb.api.controller;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerAdvice {

    private static final String REASON = "reason";
    private static final String STATUS = "status";

    private final MessageSource messageSource;

    public ControllerAdvice(MessageSource messageSource) {
        this.messageSource = messageSource;
    }


    @ExceptionHandler(BindException.class)
    public ResponseEntity<Object> validatorExceptionHandler(BindException ex) {
        final List<String> reasons = ex.getAllErrors().stream().map(error -> messageSource.getMessage(error, LocaleContextHolder.getLocale())).collect(Collectors.toList());
        final HashMap<String, Object> map = new HashMap<>();
        map.put(REASON, reasons);
        map.put(STATUS, HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
    }

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
        return ResponseEntity.status(status).body(body);
    }

}
