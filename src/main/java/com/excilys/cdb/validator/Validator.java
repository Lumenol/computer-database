package com.excilys.cdb.validator;

public interface Validator<T> {
    void check(T toValidate);
}
