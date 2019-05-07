package com.excilys.cdb.persistance.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.excilys.cdb.database.UTDatabase;
import com.excilys.cdb.exception.ComputerDAOException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Computer.ComputerBuilder;
import com.excilys.cdb.persistence.dao.ComputerDAO;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class ComputerDAOTest {

    public Object[] provideComputerId() {
	final Stream.Builder<Long> builder = Stream.builder();
	final List<Computer> allComputers = UTDatabase.getInstance().findAllComputers();
	final Computer lastComputers = allComputers.get(allComputers.size() - 1);
	for (long i = -5; i < lastComputers.getId() + 5; i++) {
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
    public void create() {
	final Company company = UTDatabase.getInstance().findCompanyById(5L);
	final ComputerBuilder builder = Computer.builder().introduced(LocalDate.of(2012, 4, 14))
		.discontinued(LocalDate.of(2020, 5, 10)).name("Le modifié").manufacturer(company);
	final Computer cree = builder.build();
	final long id = ComputerDAO.getInstance().create(cree);

	final Computer expected = builder.id(id).build();
	final Computer actual = ComputerDAO.getInstance().findById(id).get();
	assertEquals(expected, actual);
    }

    @Test(expected = ComputerDAOException.class)
    public void createWithForeinKeyConflict() {
	final Company company = Company.builder().id(404L).name("La company").build();
	final ComputerBuilder builder = Computer.builder().introduced(LocalDate.of(2012, 4, 14))
		.discontinued(LocalDate.of(2020, 5, 10)).name("Le modifié").manufacturer(company);
	final Computer cree = builder.build();
	ComputerDAO.getInstance().create(cree);
    }

    @Test
    public void deleteById() {
	final int id = 5;
	final Optional<Computer> le5avant = ComputerDAO.getInstance().findById(id);
	ComputerDAO.getInstance().deleteById(id);
	final Optional<Computer> le5apres = ComputerDAO.getInstance().findById(id);
	assertTrue(le5avant.isPresent());
	assertFalse(le5apres.isPresent());
    }

    @Test
    @Parameters(method = "provideOffsetLimit")
    public void findAll(long offset, long limit) {
	final List<Computer> actual = ComputerDAO.getInstance().findAll(offset, limit);
	final List<Computer> expected = UTDatabase.getInstance().findAllComputers(offset, limit);
	assertEquals(expected, actual);
    }

    @Test
    @Parameters(method = "provideComputerId")
    public void findById(long id) {
	final Optional<Computer> expected = Optional.ofNullable(UTDatabase.getInstance().findComputerById(id));
	final Optional<Computer> actual = ComputerDAO.getInstance().findById(id);
	assertEquals(expected, actual);
    }

    @Test
    public void update() {
	final long id = 5;
	final Computer expected = Computer.builder().id(id).introduced(LocalDate.of(2012, 4, 14))
		.discontinued(LocalDate.of(2020, 5, 10)).name("Le modifié").build();
	ComputerDAO.getInstance().update(expected);
	final Computer actual = ComputerDAO.getInstance().findById(id).get();
	assertEquals(expected, actual);
    }

    @Test
    public void count() {
	final long count = ComputerDAO.getInstance().count();
	assertEquals(UTDatabase.getInstance().findAllComputers().size(), count);
    }

    @Test
    @Parameters(value = { "Apple | 11", "App | 11", "aPp | 11" })
    public void countSearch(String search, long expected) {
	final long actual = ComputerDAO.getInstance().countSearch(search);
	assertEquals(expected, actual);
    }

    @Test
    @Parameters(value = { "0 | 2 | Apple", "10 | 4 | Apple", "0 | 5 | pLe", "5 | 10 | ApPl" })
    public void search(long offset, long limit, String search) {
	final List<Computer> actual = ComputerDAO.getInstance().search(offset, limit, search);
	final List<Computer> expected = UTDatabase.getInstance().searchComputer(offset, limit, search);
	assertEquals(expected, actual);
    }
}
