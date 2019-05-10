package com.excilys.cdb.mapper.resultset;

import static org.junit.Assert.assertEquals;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import com.excilys.cdb.model.Company;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class ResultSetToCompanyMapperTest {

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";

    private ResultSetToCompanyMapper resultSetToCompanyMapper;

    private ResultSet mockResultSet;

    public Object[] provideCompany() {
	return new Object[][] { { 1l, "La premiere" }, { 2l, "Le dexieme" }, { 3l, "Le3eme" }, { 4l, "" } };
    }

    @Before
    public void mockResultSet() {
	mockResultSet = Mockito.mock(ResultSet.class);
    }

    @Test
    @Parameters(method = "provideCompany")
    public void testMap(long id, String name) throws SQLException {
	Mockito.when(mockResultSet.getLong(COLUMN_ID)).thenReturn(id);
	Mockito.when(mockResultSet.getString(COLUMN_NAME)).thenReturn(name);

	Company company = resultSetToCompanyMapper.map(mockResultSet);
	Company expectedCompany = Company.builder().id(id).name(name).build();

	assertEquals(expectedCompany, company);
    }

}
