package com.excilys.cdb.dao;

import java.util.List;
import java.util.Optional;

final class DAOUtils {
    private DAOUtils() {
    }

    static <T> Optional<T> haveOneOrEmpty(List<T> list) {
        if (list.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(list.get(0));
        }
    }
}
