package com.excilys.cdb.validator;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

public abstract class Validator<T> {

    public final Result check(T toValidate) {
        return new Result(validation(toValidate));
    }

    protected abstract Map<String, String> validation(T toValidate);

    public static class Result {

        private final Map<String, String> errors;

        private Result(Map<String, String> errors) {
            this.errors = errors;
        }

        public void forEach(BiConsumer<? super String, ? super String> arg0) {
            errors.forEach(arg0);
        }

        public String get(String field) {
            return errors.get(field);
        }

        public Set<String> keySet() {
            return errors.keySet();
        }

        public Collection<String> values() {
            return errors.values();
        }

        public boolean isValid() {
            return errors.isEmpty();
        }
    }

}
