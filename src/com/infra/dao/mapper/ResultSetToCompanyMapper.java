package com.infra.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Function;

import com.business.entite.Company;
import com.business.exception.MappingException;

public class ResultSetToCompanyMapper implements Function<ResultSet, Company> {

    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_ID = "id";

    @Override
    public Company apply(ResultSet resultSet) {
	try {
	    long id = resultSet.getLong(COLUMN_ID);
	    String name = resultSet.getString(COLUMN_NAME);
	    return Company.builder().id(id).name(name).build();
	} catch (SQLException e) {
	    throw new MappingException(e);
	}
    }

}
