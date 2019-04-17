package com.infra.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultSetMapper<T> {
    T map(ResultSet rs) throws SQLException;
    static ResultSetMapper<ResultSet> identity(){
	return rs->rs;
    }
}
