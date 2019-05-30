package com.excilys.cdb.shared.logexception;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface WrapException {
    Class<? extends RuntimeException> value();

    Class<? extends Throwable> filter() default Exception.class;
}
