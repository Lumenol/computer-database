package com.infra.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.business.entite.Company;

public class ResultSetToListCompanyMapper implements ResultSetMapper<List<Company>> {

    private final ResultSetMapper<Company> resultSetToCompany;

    public ResultSetToListCompanyMapper(ResultSetMapper<Company> resultSetToCompany) {
	super();
	this.resultSetToCompany = resultSetToCompany;
    }

    @Override
    public List<Company> map(ResultSet rs) throws SQLException {
	List<Company> companies = new ArrayList<>();
	while (rs.next()) {
	    companies.add(resultSetToCompany.map(rs));
	}
	return companies;
    }

}
