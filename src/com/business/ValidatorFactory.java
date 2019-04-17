package com.business;

import com.business.validator.Validator;

public interface ValidatorFactory<T> {
    Validator<T> get(T dto);
}
