package com.excilys.cdb.persistence.dao;

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
import com.excilys.cdb.persistence.page.Page;

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

    public Object[] providePageLimit() {
	return new Object[][] { { 1, 30 }, { 1, 5 }, { 3, 5 }, { 2, 10 } };
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
    @Parameters(method = "providePageLimit")
    public void findAll(long index, long limit) {
	final Page page = Page.builder().page(index).limit(limit).build();
	final List<Company> actual = CompanyDAO.getInstance().findAll(page);
	final List<Company> expected = UTDatabase.getInstance().findAllCompanies(index, limit);
	assertEquals(expected, actual);
    }

    @Test
    public void findAll() {
	final List<Company> actual = CompanyDAO.getInstance().findAll();
	final List<Company> expected = UTDatabase.getInstance().findAllCompanies();
	assertEquals(expected, actual);
    }

    @Test
    public void count() {
	final long count = CompanyDAO.getInstance().count();
	assertEquals(UTDatabase.getInstance().findAllCompanies().size(), count);
    }
}
