package com.excilys.cdb.exception;

import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;

public class ValidatorException extends RuntimeException {

    private final Map<String, String> errors;

    public ValidatorException(Map<String, String> errors) {
	Objects.requireNonNull(errors);
	this.errors = errors;
    }

    public Map<String, String> getErrors() {
	return errors;
    }

    @Override
    public String getMessage() {
	StringJoiner identity = new StringJoiner(String.valueOf(Character.LINE_SEPARATOR));
	BiFunction<StringJoiner, String, StringJoiner> accumulator = (StringJoiner sj, String s) -> sj.add(s);
	BinaryOperator<StringJoiner> combiner = (StringJoiner sj2, StringJoiner sj1) -> sj1.merge(sj2);
	return errors.values().stream().reduce(identity, accumulator, combiner).toString();
    }

}
