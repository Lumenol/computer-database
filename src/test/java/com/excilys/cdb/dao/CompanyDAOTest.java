package com.excilys.cdb.dao;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.excilys.cdb.database.UTDatabase;
import com.excilys.cdb.model.Company;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class CompanyDAOTest {

    public Object[] provideCompanyId() {
	final Stream.Builder<Long> builder = Stream.builder();
	final List<Company> companies = UTDatabase.getInstance().findAllCompanies();
	final Company lastCcompanies = companies.get(companies.size() - 1);
	for (long i = -5; i < lastCcompanies.getId() + 5; i++) {
	    builder.add(i);
	}
	return builder.build().toArray();
    }

    public Object[] provideOffsetLimit() {
	return new Object[][] { { 0, 30 }, { 0, 5 }, { 5, 5 }, { 13, 25 } };
    }

    @Before
    public void loadEnttries() throws IOException, SQLException {
	UTDatabase.getInstance().reload();
    }

    @Test
    @Parameters(method = "provideCompanyId")
    public void findById(long id) {
	final Optional<Company> expected = Optional.ofNullable(UTDatabase.getInstance().findCompanyById(id));
	final Optional<Company> actual = CompanyDAO.getInstance().findById(id);
	assertEquals(expected, actual);
    }

    @Test
    @Parameters(method = "provideOffsetLimit")
    public void findAll(long offset, long limit) {
	final List<Company> actual = CompanyDAO.getInstance().findAll(offset, limit);
	final List<Company> expected = UTDatabase.getInstance().findAllCompanies(offset, limit);
	assertEquals(expected, actual);
    }

    @Test
    public void count() {
	final long count = CompanyDAO.getInstance().count();
	assertEquals(UTDatabase.getInstance().findAllCompanies().size(), count);
    }
}
