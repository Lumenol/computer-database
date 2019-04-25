package com.excilys.cdb.mapper.resultset;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ResultSetToListMapper<T> implements ResultSetMapper<List<T>> {

    private final ResultSetMapper<T> resultSetMapper;

    public ResultSetToListMapper(ResultSetMapper<T> resultSetMapper) {
	this.resultSetMapper = resultSetMapper;
    }

    @Override
    public List<T> map(ResultSet rs) throws SQLException {
	List<T> list = new ArrayList<>();
	while (rs.next()) {
	    list.add(resultSetMapper.map(rs));
	}
	return list;
    }

}
