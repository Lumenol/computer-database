package com.excilys.cdb.mapper.resultset;

import static org.junit.Assert.assertEquals;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Computer.ComputerBuilder;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class ResultSetToComputerMapperTest {

    private static final String COLUMN_COMPANY_ID = "company_id";
    private static final String COLUMN_COMPANY_NAME = "company_name";
    private static final String COLUMN_DISCONTINUED = "discontinued";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_INTRODUCED = "introduced";
    private static final String COLUMN_NAME = "name";

    private ResultSetToComputerMapper resultSetToComputerMapper;

    private ResultSet mockResultSet;

    public Object[] provideComputer() {
	return new Object[][] { { 1l, "Le premier", null, null, 4l, "4eme corp" },
		{ 2l, "Le dexieme", LocalDate.of(2017, 10, 10), null, null, null },
		{ 3l, "Le 3eme", LocalDate.of(2016, 9, 9), LocalDate.of(2017, 10, 10), 5l, "Une autre corp" },
		{ 4l, "Le 4eme", null, LocalDate.of(2017, 10, 10), 6l, "La 6eme" } };
    }

    @Before
    public void mockResultSet() {
	mockResultSet = Mockito.mock(ResultSet.class);
    }

    @Test
    @Parameters(method = "provideComputer")
    public void testMap(long id, String name, LocalDate introduced, LocalDate discontinued, Long companyId,
	    String companyName) throws SQLException {

	Mockito.when(mockResultSet.getLong(COLUMN_ID)).thenReturn(id);
	Mockito.when(mockResultSet.getString(COLUMN_NAME)).thenReturn(name);

	Date introducedSQL = Objects.nonNull(introduced) ? Date.valueOf(introduced) : null;
	Mockito.when(mockResultSet.getDate(COLUMN_INTRODUCED)).thenReturn(introducedSQL);

	Date discontinuedSQL = Objects.nonNull(discontinued) ? Date.valueOf(discontinued) : null;
	Mockito.when(mockResultSet.getDate(COLUMN_DISCONTINUED)).thenReturn(discontinuedSQL);

	long companyIdSQL = Objects.nonNull(companyId) ? companyId : 0;
	Mockito.when(mockResultSet.getLong(COLUMN_COMPANY_ID)).thenReturn(companyIdSQL);

	Mockito.when(mockResultSet.getString(COLUMN_COMPANY_NAME)).thenReturn(companyName);

	Computer computer = resultSetToComputerMapper.map(mockResultSet);

	ComputerBuilder computerBuilder = Computer.builder().id(id).name(name).introduced(introduced)
		.discontinued(discontinued);
	if (Objects.nonNull(companyId)) {
	    Company company = Company.builder().id(companyId).name(companyName).build();
	    computerBuilder.manufacturer(company);
	}
	Computer expectedComputer = computerBuilder.build();

	assertEquals(expectedComputer, computer);
    }
}