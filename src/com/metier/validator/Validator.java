package com.metier.validator;

public interface Validator<T> {
	boolean isValid(T in);
}
