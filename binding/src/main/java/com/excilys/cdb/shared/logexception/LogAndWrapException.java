package com.excilys.cdb.shared.logexception;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogAndWrapException {
    Class logger();

    Class<? extends RuntimeException> exception();

    Class<? extends Throwable> filter() default Exception.class;
}
