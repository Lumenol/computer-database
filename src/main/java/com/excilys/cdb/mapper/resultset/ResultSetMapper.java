package com.excilys.cdb.mapper.resultset;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultSetMapper<T> {
    static ResultSetMapper<ResultSet> identity() {
        return rs -> rs;
    }

    T map(ResultSet rs) throws SQLException;
}
