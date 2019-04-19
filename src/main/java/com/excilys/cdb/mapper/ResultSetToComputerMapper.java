package com.excilys.cdb.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Computer.ComputerBuilder;

public class ResultSetToComputerMapper implements ResultSetMapper<Computer> {

    private static final String COLUMN_COMPANY_ID = "company_id";
    private static final String COLUMN_COMPANY_NAME = "company_name";
    private static final String COLUMN_DISCONTINUED = "discontinued";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_INTRODUCED = "introduced";
    private static final String COLUMN_NAME = "name";

    @Override
    public Computer map(ResultSet rs) throws SQLException {
	long id = rs.getLong(COLUMN_ID);
	String name = rs.getString(COLUMN_NAME);
	LocalDate introduced = Optional.ofNullable(rs.getDate(COLUMN_INTRODUCED)).map(Date::toLocalDate).orElse(null);
	LocalDate discontinued = Optional.ofNullable(rs.getDate(COLUMN_DISCONTINUED)).map(Date::toLocalDate)
		.orElse(null);
	ComputerBuilder computerBuilder = Computer.builder().id(id).name(name).introduced(introduced)
		.discontinued(discontinued);
	Long companyId = rs.getLong(COLUMN_COMPANY_ID);
	String compagnyName = rs.getString(COLUMN_COMPANY_NAME);
	if (Objects.nonNull(companyId) && Objects.nonNull(compagnyName)) {
	    Company mannufacturer = Company.builder().id(companyId).name(compagnyName).build();
	    computerBuilder.manufacturer(mannufacturer);
	}
	return computerBuilder.build();
    }

}
