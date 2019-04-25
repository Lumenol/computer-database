package com.excilys.cdb.mapper.resultset;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import com.excilys.cdb.model.Company;

public class ResultSetToCompanyMapper implements ResultSetMapper<Company> {

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static ResultSetToCompanyMapper instance;

    public static ResultSetToCompanyMapper getInstance() {
	if (Objects.isNull(instance)) {
	    instance = new ResultSetToCompanyMapper();
	}
	return instance;
    }

    private ResultSetToCompanyMapper() {
    }

    @Override
    public Company map(ResultSet rs) throws SQLException {
	long id = rs.getLong(COLUMN_ID);
	String name = rs.getString(COLUMN_NAME);
	return Company.builder().id(id).name(name).build();
    }

}
