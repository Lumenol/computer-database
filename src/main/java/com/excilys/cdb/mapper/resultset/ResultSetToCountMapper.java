package com.excilys.cdb.mapper.resultset;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

@Component
public class ResultSetToCountMapper implements ResultSetMapper<Long> {

    @Override
    public Long map(ResultSet rs) throws SQLException {
	rs.first();
	return rs.getLong(1);
    }

}
