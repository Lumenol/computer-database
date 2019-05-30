package com.excilys.cdb.shared.logexception;

import com.excilys.cdb.shared.exception.ConstructorWithCauseNotFound;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Aspect
@Component
public class ExceptionAspect {

    @AfterThrowing(value = "@annotation(LogException) && @annotation(a)", throwing = "ex")
    public void log(JoinPoint jp, LogException a, Throwable ex) {
        if (a.filter().isAssignableFrom(ex.getClass())) {
            log(jp, ex, a.value());
        }
    }

    private void log(JoinPoint jp, Throwable ex, Class logger) {
        final List<String> args = Arrays.stream(jp.getArgs()).map(Object::toString).collect(Collectors.toList());
        final String message = String.format("%s(%s)", jp.getSignature().getName(), String.join(", ", args));
        LoggerFactory.getLogger(logger).error(message, ex);
    }

    @AfterThrowing(value = "@annotation(WrapException) && @annotation(a)", throwing = "ex")
    public void wrap(JoinPoint jp, WrapException a, Throwable ex) {
        if (a.filter().isAssignableFrom(ex.getClass())) {
            wrap(ex, a.value());
        }
    }

    private void wrap(Throwable ex, Class<? extends RuntimeException> value) {
        try {
            final Constructor<? extends RuntimeException> constructor = value.getConstructor(Throwable.class);
            throw constructor.newInstance(ex);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new ConstructorWithCauseNotFound(e);
        }
    }

    @AfterThrowing(value = "@annotation(LogAndWrapException) && @annotation(a)", throwing = "ex")
    public void log(JoinPoint jp, LogAndWrapException a, Throwable ex) {
        if (a.filter().isAssignableFrom(ex.getClass())) {
            log(jp, ex, a.logger());
            wrap(ex, a.exception());
        }
    }

}
