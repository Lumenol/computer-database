package com.excilys.cdb.controller.web;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
class GlobalDefaultExceptionHandler {
    public static final String DEFAULT_ERROR_VIEW = "error";

    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) {
	String message = HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
	int code = HttpStatus.INTERNAL_SERVER_ERROR.value();
	final ResponseStatus response = AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class);
	if (Objects.nonNull(response)) {
	    code = response.code().value();
	    message = response.reason();
	    if (message.isEmpty()) {
		message = response.code().getReasonPhrase();
	    }
	} else if (e instanceof NoHandlerFoundException) {
	    code = HttpStatus.NOT_FOUND.value();
	    message = HttpStatus.NOT_FOUND.getReasonPhrase();
	}

	ModelAndView mav = new ModelAndView(DEFAULT_ERROR_VIEW);
	mav.addObject("exception", e);
	mav.addObject("url", req.getRequestURL());
	mav.addObject("code", code);
	mav.addObject("message", message);
	return mav;
    }
}