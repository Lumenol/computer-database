package com.excilys.cdb.persistence.dao;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Computer.ComputerBuilder;
import com.excilys.cdb.persistence.config.PersistenceConfigTest;
import com.excilys.cdb.persistence.exception.ComputerDAOException;
import com.excilys.cdb.shared.pagination.OrderBy;
import com.excilys.cdb.shared.pagination.Page;
import com.excilys.cdb.shared.pagination.Pageable;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

@RunWith(JUnitParamsRunner.class)
@ContextConfiguration(classes = PersistenceConfigTest.class)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "entries.sql")
public class ComputerDAOTest {
    @ClassRule
    public static final SpringClassRule springClassRule = new SpringClassRule();
    @Rule
    public final SpringMethodRule springMethodRule = new SpringMethodRule();

    private ComputerDAO computerDAO;
	private UTDatabase database = UTDatabase.getInstance();

    @Autowired
    public void setComputerDAO(ComputerDAO computerDAO) {
	this.computerDAO = computerDAO;
    }

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
	    for (OrderBy.Direction direction : OrderBy.Direction.values()) {
		for (long[] indices : limits) {
		    final OrderBy orderBy = OrderBy.builder().field(field).direction(direction).build();
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
	    for (OrderBy.Direction direction : OrderBy.Direction.values()) {
		for (long[] indices : limits) {
		    final OrderBy orderBy = OrderBy.builder().field(field).direction(direction).build();
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
	final long actual = computerDAO.countByNameOrCompanyName(search);
	assertEquals(expected, actual);
    }

    @Test
    @Parameters(method = "providePageableAndSearch")
    public void search(Pageable pageable, String search) {
	final List<Computer> actual = computerDAO.searchByNameOrCompanyName(pageable, search);
	final List<Computer> expected = database.searchComputer(pageable, search);

	assertEquals(expected, actual);
    }

    @Test
    public void deleteComputerById() {
	final int id = 1;
	final List<Computer> computersOfCompany = database.findAllComputers().stream()
		.filter(c -> Objects.nonNull(c.getManufacturer()) && c.getManufacturer().getId().equals((long) id))
		.collect(Collectors.toList());
	assertTrue("Les ordinateurs ne sont pas dans la bases avant la supression.", computersOfCompany.stream()
		.map(Computer::getId).map(computerDAO::findById).allMatch(Optional::isPresent));

	computerDAO.deleteBymanufacturerId(id);

	assertFalse("Les ordinateurs sont dans la bases après la supression.", computersOfCompany.stream()
		.map(Computer::getId).map(computerDAO::findById).anyMatch(Optional::isPresent));
    }
}
