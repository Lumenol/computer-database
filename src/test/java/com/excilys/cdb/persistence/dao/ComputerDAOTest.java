package com.excilys.cdb.persistence.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;

import com.excilys.cdb.config.TestConfig;
import com.excilys.cdb.database.UTDatabase;
import com.excilys.cdb.exception.ComputerDAOException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Computer.ComputerBuilder;
import com.excilys.cdb.persistence.page.OrderBy;
import com.excilys.cdb.persistence.page.Page;
import com.excilys.cdb.persistence.page.Pageable;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class ComputerDAOTest {
    @ClassRule
    public static final SpringClassRule springClassRule = new SpringClassRule();
    @Rule
    public final SpringMethodRule springMethodRule = new SpringMethodRule();

    @Autowired
    private ComputerDAO computerDAO;
    @Autowired
    private UTDatabase database;

    public Object[] provideComputerId() {
	final Stream.Builder<Long> builder = Stream.builder();
	for (long i = 1; i < 30; i++) {
	    builder.add(i);
	}
	return builder.build().toArray();
    }

    public Object[] providePageable() {
	final ArrayList<Object[]> params = new ArrayList<>();
	final long[][] limits = { { 1L, 10L }, { 5L, 2L }, { 2L, 3L }, { 2L, 7L } };
	for (OrderBy.Field field : OrderBy.Field.values()) {
	    for (OrderBy.Meaning meaning : OrderBy.Meaning.values()) {
		for (long[] indices : limits) {
		    final OrderBy orderBy = OrderBy.builder().field(field).meaning(meaning).build();
		    Page page = Page.builder().page(indices[0]).size(indices[1]).build();
		    final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
		    params.add(new Object[] { pageable });
		}
	    }
	}

	return params.toArray();
    }

    public Object[] providePageableAndSearch() {
	final ArrayList<Object[]> params = new ArrayList<>();
	final long[][] limits = { { 1L, 30L }, { 1L, 5L }, { 4L, 5L }, { 2L, 3L }, { 13L, 25L } };
	final String[] search = { "Apple", "pLe", "ApPl", "bAn", "Bo", "Je existe pas" };
	for (OrderBy.Field field : OrderBy.Field.values()) {
	    for (OrderBy.Meaning meaning : OrderBy.Meaning.values()) {
		for (long[] indices : limits) {
		    final OrderBy orderBy = OrderBy.builder().field(field).meaning(meaning).build();
		    Page page = Page.builder().page(indices[0]).size(indices[1]).build();
		    final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
		    for (String s : search) {
			params.add(new Object[] { pageable, s });
		    }
		}
	    }
	}
	return params.toArray();
    }

    @Before
    public void loadEnttries() throws IOException, SQLException {
	database.reload();
    }

    @Test
    public void create() {
	final Company company = database.findCompanyById(5L);
	final ComputerBuilder builder = Computer.builder().introduced(LocalDate.of(2012, 4, 14))
		.discontinued(LocalDate.of(2020, 5, 10)).name("Le modifié").manufacturer(company);
	final Computer cree = builder.build();
	final long id = computerDAO.create(cree);

	final Computer expected = builder.id(id).build();
	final Computer actual = computerDAO.findById(id).get();
	assertEquals(expected, actual);
    }

    @Test(expected = ComputerDAOException.class)
    public void createWithForeinKeyConflict() {
	final Company company = Company.builder().id(404L).name("La company").build();
	final ComputerBuilder builder = Computer.builder().introduced(LocalDate.of(2012, 4, 14))
		.discontinued(LocalDate.of(2020, 5, 10)).name("Le modifié").manufacturer(company);
	final Computer cree = builder.build();
	computerDAO.create(cree);
    }

    @Test
    public void deleteById() {
	final int id = 5;
	final Optional<Computer> le5avant = computerDAO.findById(id);
	computerDAO.deleteById(id);
	final Optional<Computer> le5apres = computerDAO.findById(id);
	assertTrue(le5avant.isPresent());
	assertFalse(le5apres.isPresent());
    }

    @Test
    @Parameters(method = "providePageable")
    public void findAll(Pageable pageable) {
	final List<Computer> actual = computerDAO.findAll(pageable);
	final List<Computer> expected = database.findAllComputers(pageable);
	assertEquals(expected, actual);
    }

    @Test
    @Parameters(method = "provideComputerId")
    public void findById(long id) {
	final Optional<Computer> expected = Optional.ofNullable(database.findComputerById(id));
	final Optional<Computer> actual = computerDAO.findById(id);
	assertEquals(expected, actual);
    }

    @Test
    public void update() {
	final long id = 5;
	final Computer expected = Computer.builder().id(id).introduced(LocalDate.of(2012, 4, 14))
		.discontinued(LocalDate.of(2020, 5, 10)).name("Le modifié").build();
	computerDAO.update(expected);
	final Computer actual = computerDAO.findById(id).get();
	assertEquals(expected, actual);
    }

    @Test
    public void count() {
	final long count = computerDAO.count();
	assertEquals(database.findAllComputers().size(), count);
    }

    @Test
    @Parameters(value = { "Apple | 11", "App | 11", "aPp | 11" })
    public void countSearch(String search, long expected) {
	final long actual = computerDAO.countSearch(search);
	assertEquals(expected, actual);
    }

    @Test
    @Parameters(method = "providePageableAndSearch")
    public void search(Pageable pageable, String search) {
	final List<Computer> actual = computerDAO.search(pageable, search);
	final List<Computer> expected = database.searchComputer(pageable, search);
	assertEquals(expected, actual);
    }
}
