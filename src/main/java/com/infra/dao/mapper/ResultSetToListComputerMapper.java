package com.infra.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.business.entite.Computer;

public class ResultSetToListComputerMapper implements ResultSetMapper<List<Computer>> {

    private final ResultSetMapper<Computer> resultSetToComputer;

    public ResultSetToListComputerMapper(ResultSetMapper<Computer> resultSetToComputer) {
	super();
	this.resultSetToComputer = resultSetToComputer;
    }

    @Override
    public List<Computer> map(ResultSet rs) throws SQLException {
	List<Computer> companies = new ArrayList<>();
	while (rs.next()) {
	    companies.add(resultSetToComputer.map(rs));
	}
	return companies;
    }

}
