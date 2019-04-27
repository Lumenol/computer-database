package com.excilys.cdb.dao;

import com.excilys.cdb.TestDatabase;
import com.excilys.cdb.exception.ComputerDAOException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Computer.ComputerBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ComputerDAOTest {

    private static Stream<Long> provideComputerId() {
        final Stream.Builder<Long> builder = Stream.builder();
        final List<Computer> allComputers = TestDatabase.getInstance().findAllComputers();
        final Computer lastComputers = allComputers.get(allComputers.size() - 1);
        for (long i = -5; i < lastComputers.getId() + 5; i++) {
            builder.add(i);
        }
        return builder.build();
    }

    private static Stream<Arguments> provideOffsetLimit() {
        return Stream.of(Arguments.of(0, 30), Arguments.of(0, 5), Arguments.of(5, 5), Arguments.of(13, 25));
    }

    @BeforeEach
    public void loadEnttries() throws IOException, SQLException {
        TestDatabase.getInstance().reload();
    }

    @Test
    void create() {
        final Company company = TestDatabase.getInstance().findCompanyById(5L);
        final ComputerBuilder builder = Computer.builder().introduced(LocalDate.of(2012, 4, 14))
                .discontinued(LocalDate.of(2020, 5, 10)).name("Le modifié").manufacturer(company);
        final Computer cree = builder.build();
        final long id = ComputerDAO.getInstance().create(cree);

        final Computer expected = builder.id(id).build();
        final Computer actual = ComputerDAO.getInstance().findById(id).get();
        assertEquals(expected, actual);
    }

    @Test
    void createWithForeinKeyConflict() {
        final Company company = Company.builder().id(404).name("La company").build();
        final ComputerBuilder builder = Computer.builder().introduced(LocalDate.of(2012, 4, 14))
                .discontinued(LocalDate.of(2020, 5, 10)).name("Le modifié").manufacturer(company);
        final Computer cree = builder.build();
        assertThrows(ComputerDAOException.class, () -> ComputerDAO.getInstance().create(cree));
    }

    @Test
    void deleteById() {
        final int id = 5;
        final Optional<Computer> le5avant = ComputerDAO.getInstance().findById(id);
        ComputerDAO.getInstance().deleteById(id);
        final Optional<Computer> le5apres = ComputerDAO.getInstance().findById(id);
        assertTrue(le5avant.isPresent());
        assertFalse(le5apres.isPresent());
    }

    @ParameterizedTest
    @MethodSource("provideOffsetLimit")
    void findAll(long offset, long limit) {
        final List<Computer> actual = ComputerDAO.getInstance().findAll(offset, limit);
        final List<Computer> expected = TestDatabase.getInstance().findAllComputers(offset, limit);
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("provideComputerId")
    void findById(long id) {
        final Optional<Computer> expected = Optional.ofNullable(TestDatabase.getInstance().findComputerById(id));
        final Optional<Computer> actual = ComputerDAO.getInstance().findById(id);
        assertEquals(expected, actual);
    }

    @Test
    void update() {
        final int id = 5;
        final Computer expected = Computer.builder().id(id).introduced(LocalDate.of(2012, 4, 14))
                .discontinued(LocalDate.of(2020, id, 10)).name("Le modifié").build();
        ComputerDAO.getInstance().update(expected);
        final Computer actual = ComputerDAO.getInstance().findById(id).get();
        assertEquals(expected, actual);
    }

    @Test
    void count() {
        final long count = ComputerDAO.getInstance().count();
        assertEquals(TestDatabase.getInstance().findAllComputers().size(), count);
    }
}
