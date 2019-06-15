package com.excilys.cdb.api.controller;

import com.excilys.cdb.api.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerAdvice extends ResponseEntityExceptionHandler {

    private static final String REASON = "reason";
    private static final String STATUS = "status";
    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerAdvice.class);
    private final MessageSource messageSource;

    public ControllerAdvice(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> accessDeniedHandler(AccessDeniedException ex) {
        final HttpStatus status = HttpStatus.UNAUTHORIZED;
        final HashMap<String, Object> body = new HashMap<>();
        body.put(REASON, ex.getLocalizedMessage());
        body.put(STATUS, status.value());
        final String login = ServletUriComponentsBuilder.fromCurrentContextPath().path("/login").build().toString();
        return ResponseEntity.status(status).header(HttpHeaders.LINK, Utils.createLink(login, "login")).body(body);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> badCredencialsdHandler(BadCredentialsException ex) {
        final HttpStatus status = HttpStatus.UNAUTHORIZED;
        final HashMap<String, Object> body = new HashMap<>();
        body.put(REASON, ex.getLocalizedMessage());
        body.put(STATUS, status.value());
        return ResponseEntity.status(status).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> defaultHandleException(Exception ex) {
        LOGGER.warn("defaultHandleException: ", ex);
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

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        final List<String> reasons = ex.getAllErrors().stream().map(error -> messageSource.getMessage(error, LocaleContextHolder.getLocale())).collect(Collectors.toList());
        final HashMap<String, Object> body = new HashMap<>();
        body.put(REASON, reasons);
        body.put(STATUS, status.value());
        return ResponseEntity.status(status).headers(headers).body(body);
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
