package com.excilys.cdb.mapper.resultset;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

import com.excilys.cdb.model.Company;

@Component
public class ResultSetToCompanyMapper implements ResultSetMapper<Company> {

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";

    @Override
    public Company map(ResultSet rs) throws SQLException {
	long id = rs.getLong(COLUMN_ID);
	String name = rs.getString(COLUMN_NAME);
	return Company.builder().id(id).name(name).build();
    }

}
