package com.excilys.cdb.persistence.dao;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Computer.ComputerBuilder;
import com.excilys.cdb.persistence.configuration.PersistenceConfigurationTest;
import com.excilys.cdb.persistence.exception.ComputerDAOException;
import com.excilys.cdb.shared.pagination.OrderBy;
import com.excilys.cdb.shared.pagination.Page;
import com.excilys.cdb.shared.pagination.Pageable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = PersistenceConfigurationTest.class)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "entries.sql")
public class ComputerDAOTest {
    private ComputerDAO computerDAO;
    private UTDatabase database = UTDatabase.getInstance();

    public static Stream<Long> provideComputerId() {
        final Stream.Builder<Long> builder = Stream.builder();
        for (long i = 1; i < 30; i++) {
            builder.add(i);
        }
        return builder.build();
    }

    public static Stream<Pageable> providePageable() {
        final Stream.Builder<Pageable> params = Stream.builder();
        final long[][] limits = {{1L, 10L}, {5L, 2L}, {2L, 3L}, {2L, 7L}};
        for (OrderBy.Field field : OrderBy.Field.values()) {
            for (OrderBy.Direction direction : OrderBy.Direction.values()) {
                for (long[] indices : limits) {
                    final OrderBy orderBy = OrderBy.builder().field(field).direction(direction).build();
                    Page page = Page.builder().page(indices[0]).size(indices[1]).build();
                    final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
                    params.add(pageable);
                }
            }
        }

        return params.build();
    }

    public static Stream<Arguments> providePageableAndSearch() {
        final Stream.Builder<Arguments> params = Stream.builder();
        final long[][] limits = {{1L, 30L}, {1L, 5L}, {4L, 5L}, {2L, 3L}, {13L, 25L}};
        final String[] search = {"Apple", "pLe", "ApPl", "bAn", "Bo", "Je existe pas"};
        for (OrderBy.Field field : OrderBy.Field.values()) {
            for (OrderBy.Direction direction : OrderBy.Direction.values()) {
                for (long[] indices : limits) {
                    final OrderBy orderBy = OrderBy.builder().field(field).direction(direction).build();
                    Page page = Page.builder().page(indices[0]).size(indices[1]).build();
                    final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
                    for (String s : search) {
                        params.add(Arguments.of(pageable, s));
                    }
                }
            }
        }
        return params.build();
    }

    @Autowired
    public void setComputerDAO(ComputerDAO computerDAO) {
        this.computerDAO = computerDAO;
    }

    @Test
    public void create() {
        final Company company = database.findCompanyById(5L);
        final ComputerBuilder builder = Computer.builder().introduced(LocalDate.of(2012, 4, 14))
                .discontinued(LocalDate.of(2020, 5, 10)).name("Le modifié").manufacturer(company);
        final Computer cree = builder.build();
        computerDAO.create(cree);

        final Computer expected = builder.id(cree.getId()).build();
        final Computer actual = computerDAO.findById(expected.getId()).get();
        assertEquals(expected, actual);
    }

    @Test
    public void createWithForeinKeyConflict() {
        final Company company = Company.builder().id(404L).name("La company").build();
        final ComputerBuilder builder = Computer.builder().introduced(LocalDate.of(2012, 4, 14))
                .discontinued(LocalDate.of(2020, 5, 10)).name("Le modifié").manufacturer(company);
        final Computer cree = builder.build();
        assertThrows(ComputerDAOException.class, () -> computerDAO.create(cree));
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

    @ParameterizedTest
    @MethodSource("providePageable")
    public void findAll(Pageable pageable) {
        final List<Computer> actual = computerDAO.findAll(pageable);
        final List<Computer> expected = database.findAllComputers(pageable);
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("provideComputerId")
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

    @ParameterizedTest
    @CsvSource(value = {"Apple | 11", "App | 11", "aPp | 11"}, delimiter = '|')
    public void countSearch(String search, long expected) {
        final long actual = computerDAO.countByNameOrCompanyName(search);
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("providePageableAndSearch")
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
        assertTrue(computersOfCompany.stream().map(Computer::getId).map(computerDAO::findById).allMatch(Optional::isPresent), "Les ordinateurs ne sont pas dans la bases avant la supression.");

        computerDAO.deleteBymanufacturerId(id);

        assertFalse(computersOfCompany.stream().map(Computer::getId).map(computerDAO::findById).anyMatch(Optional::isPresent), "Les ordinateurs sont dans la bases après la supression.");
    }

    @ParameterizedTest
    @MethodSource("provideComputerId")
    public void exist(long id) {
        final boolean expected = Optional.ofNullable(database.findComputerById(id)).isPresent();
        final boolean actual = computerDAO.exist(id);
        assertEquals(expected, actual);
    }
}
