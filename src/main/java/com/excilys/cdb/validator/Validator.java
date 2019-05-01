package com.excilys.cdb.validator;

public interface Validator<T> {
    Result check(T toValidate);
}
