package com.excilys.cdb.webapp.configuration;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

class ErrorHandler implements HandlerExceptionResolver {
    private static final String VIEW_NAME = "error";
    private static final String REASON = "reason";
    private static final String STATUS = "status";
    private final DefaultHandlerExceptionResolver defaultHandlerExceptionResolver = new DefaultHandlerExceptionResolver();

    private ModelAndView defaultResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
                                                 Exception ex) {
        int code = HttpStatus.INTERNAL_SERVER_ERROR.value();
        String message = HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
        final ModelAndView modelAndView = new ModelAndView(VIEW_NAME);

        final Optional<ResponseStatus> responseStatuts = Optional.ofNullable(ex).map(Object::getClass)
                .map(c -> c.getAnnotation(ResponseStatus.class));
        if (responseStatuts.isPresent()) {
            final ResponseStatus responseStatus = responseStatuts.get();
            code = responseStatus.code().value();
            message = responseStatus.reason().isEmpty() ? responseStatus.code().getReasonPhrase()
                    : responseStatus.reason();
        } else {
            ErrorHandler.ResponseInterceptError res = new ResponseInterceptError(response);
            defaultHandlerExceptionResolver.resolveException(request, res, handler, ex);

            if (Objects.nonNull(res.getCode())) {
                code = res.getCode();
                if (Objects.isNull(res.getMessage())) {
                    message = res.getMessage();
                } else {
                    try {
                        message = HttpStatus.valueOf(code).getReasonPhrase();
                    } catch (IllegalArgumentException e) {
                    }
                }
            }
        }
        modelAndView.addObject(STATUS, code);
        modelAndView.addObject(REASON, message);

        return modelAndView;
    }

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if (ex instanceof AccessDeniedException) {
            return accessDeniedExceptionResolver((AccessDeniedException) ex);
        } else {
            return defaultResolveException(request, response, handler, ex);
        }
    }

    private ModelAndView accessDeniedExceptionResolver(AccessDeniedException ex) {
        return new ModelAndView("redirect:/login");
    }

    private static class ResponseInterceptError implements HttpServletResponse {
        private final HttpServletResponse response;
        private Integer code;
        private String message;

        public ResponseInterceptError(HttpServletResponse response) {
            this.response = response;
        }

        @Override
        public void addCookie(Cookie cookie) {
            response.addCookie(cookie);
        }

        @Override
        public boolean containsHeader(String name) {
            return response.containsHeader(name);
        }

        @Override
        public String encodeURL(String url) {
            return response.encodeURL(url);
        }

        @Override
        public String getCharacterEncoding() {
            return response.getCharacterEncoding();
        }

        @Override
        public void setCharacterEncoding(String charset) {
            response.setCharacterEncoding(charset);
        }

        @Override
        public String encodeRedirectURL(String url) {
            return response.encodeRedirectURL(url);
        }

        @Override
        public String getContentType() {
            return response.getContentType();
        }

        @Override
        public void setContentType(String type) {
            response.setContentType(type);
        }

        @Override
        public String encodeUrl(String url) {
            return response.encodeUrl(url);
        }

        @Override
        public ServletOutputStream getOutputStream() throws IOException {
            return response.getOutputStream();
        }

        @Override
        public String encodeRedirectUrl(String url) {
            return response.encodeRedirectUrl(url);
        }

        @Override
        public PrintWriter getWriter() throws IOException {
            return response.getWriter();
        }

        @Override
        public void sendRedirect(String location) throws IOException {
            response.sendRedirect(location);
        }

        @Override
        public void setContentLength(int len) {
            response.setContentLength(len);
        }

        @Override
        public void setContentLengthLong(long len) {
            response.setContentLengthLong(len);
        }

        @Override
        public void setDateHeader(String name, long date) {
            response.setDateHeader(name, date);
        }

        @Override
        public void addDateHeader(String name, long date) {
            response.addDateHeader(name, date);
        }

        @Override
        public void setHeader(String name, String value) {
            response.setHeader(name, value);
        }

        @Override
        public void addHeader(String name, String value) {
            response.addHeader(name, value);
        }

        @Override
        public void setIntHeader(String name, int value) {
            response.setIntHeader(name, value);
        }

        @Override
        public int getBufferSize() {
            return response.getBufferSize();
        }

        @Override
        public void setBufferSize(int size) {
            response.setBufferSize(size);
        }

        @Override
        public void addIntHeader(String name, int value) {
            response.addIntHeader(name, value);
        }

        @Override
        public void flushBuffer() throws IOException {
            response.flushBuffer();
        }

        @Override
        public void resetBuffer() {
            response.resetBuffer();
        }

        @Override
        public boolean isCommitted() {
            return response.isCommitted();
        }

        @Override
        public void reset() {
            response.reset();
        }

        @Override
        public int getStatus() {
            return response.getStatus();
        }

        @Override
        public void setStatus(int sc) {
            setStatus(code = sc, null);
        }

        @Override
        public String getHeader(String name) {
            return response.getHeader(name);
        }

        @Override
        public Collection<String> getHeaders(String name) {
            return response.getHeaders(name);
        }

        @Override
        public Collection<String> getHeaderNames() {
            return response.getHeaderNames();
        }

        @Override
        public Locale getLocale() {
            return response.getLocale();
        }

        @Override
        public void setLocale(Locale loc) {
            response.setLocale(loc);
        }

        public Integer getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

        @Override
        public void sendError(int sc, String msg) throws IOException {
            setStatus(sc, msg);
        }

        @Override
        public void sendError(int sc) throws IOException {
            sendError(sc, null);
        }

        @Override
        public void setStatus(int sc, String sm) {
            response.setStatus(code = sc, message = sm);
        }

    }


}