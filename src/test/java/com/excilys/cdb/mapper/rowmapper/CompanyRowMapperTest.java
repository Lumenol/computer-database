package com.excilys.cdb.mapper.rowmapper;

import static org.junit.Assert.assertEquals;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;

import com.excilys.cdb.config.TestConfig;
import com.excilys.cdb.model.Company;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class CompanyRowMapperTest {

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";

    @ClassRule
    public static final SpringClassRule springClassRule = new SpringClassRule();
    @Rule
    public final SpringMethodRule springMethodRule = new SpringMethodRule();

    @Autowired
    private CompanyRowMapper companyRowMapper;

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

	Company company = companyRowMapper.mapRow(mockResultSet, 0);
	Company expectedCompany = Company.builder().id(id).name(name).build();

	assertEquals(expectedCompany, company);
    }

}
