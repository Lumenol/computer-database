package com.excilys.cdb.web.controller;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Controller
public class ErrorController {

    private static final String VIEW_NAME = "error";
    private static final String REASON = "reason";
    private static final String STATUS = "status";

    @RequestMapping(value = "/error")
    public ModelAndView handle(HttpServletRequest request) {

        final ModelAndView modelAndView = new ModelAndView(VIEW_NAME);
        final int code = (int) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        modelAndView.addObject(STATUS, code);
        final Exception exception = (Exception) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
        if (Objects.nonNull(exception)) {
            final ResponseStatus annotation = AnnotationUtils.findAnnotation(exception.getClass(), ResponseStatus.class);
            if (Objects.nonNull(annotation) && !annotation.reason().isEmpty()) {
                modelAndView.addObject(REASON, annotation.reason());
                return modelAndView;
            }
        } else {
            final HttpStatus httpStatus = HttpStatus.resolve(code);
            if (Objects.nonNull(httpStatus)) {
                modelAndView.addObject(REASON, httpStatus.getReasonPhrase());
                return modelAndView;
            }
        }

        return modelAndView;
    }

}