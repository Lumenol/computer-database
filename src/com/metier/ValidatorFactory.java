package com.metier;

import com.metier.validator.Validator;

public interface ValidatorFactory<T> {
	Validator<T> get(T dto);
}
