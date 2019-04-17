package com.infra.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Function;

import com.business.entite.Company;
import com.business.exception.MappingException;

public class ResultSetToCompanyMapper implements ResultSetMapper<Company> {

    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_ID = "id";

    @Override
    public Company map(ResultSet rs) throws SQLException {
	long id = rs.getLong(COLUMN_ID);
	String name = rs.getString(COLUMN_NAME);
	return Company.builder().id(id).name(name).build();
    }

}
