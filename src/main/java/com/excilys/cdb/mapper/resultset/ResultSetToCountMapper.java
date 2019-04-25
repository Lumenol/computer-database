package com.excilys.cdb.mapper.resultset;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class ResultSetToCountMapper implements ResultSetMapper<Long> {

    private static ResultSetToCountMapper instance;

    public static ResultSetToCountMapper getInstance() {
	if (Objects.isNull(instance)) {
	    instance = new ResultSetToCountMapper();
	}
	return instance;
    }

    private ResultSetToCountMapper() {
    }

    @Override
    public Long map(ResultSet rs) throws SQLException {
	rs.first();
	return rs.getLong(1);
    }

}
