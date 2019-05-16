package com.excilys.cdb.mapper.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.excilys.cdb.model.Company;

@Component
public class CompanyRowMapper implements RowMapper<Company> {

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";

    @Override
    public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
	long id = rs.getLong(COLUMN_ID);
	String name = rs.getString(COLUMN_NAME);
	return Company.builder().id(id).name(name).build();
    }

}
