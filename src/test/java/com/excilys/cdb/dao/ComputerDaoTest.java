package com.excilys.cdb.dao;

import com.excilys.cdb.model.Computer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class ComputerDaoTest {

    private static Stream<Long> provideComputerId() {
        final Stream.Builder<Long> builder = Stream.builder();
        final List<Computer> allComputers = TestDatabase.getInstance().findAllComputers();
        final Computer lastComputers = allComputers.get(allComputers.size() - 1);
        for (long i = -5; i < lastComputers.getId() + 5; i++) {
            builder.add(i);
        }
        return builder.build();
    }

    @BeforeEach
    public void loadEnttries() throws IOException, SQLException {
        TestDatabase.getInstance().reload();
    }

    @Test
    void create() {
        fail();

    }

    @Test
    void deleteById() {
        fail();

    }

    @Test
    void findAll() {
        fail();

    }

    @ParameterizedTest
    @MethodSource("provideComputerId")
    void findById(long id) {
        final Optional<Computer> expected = Optional.ofNullable(TestDatabase.getInstance().findComputerById(id));
        final Optional<Computer> actual = ComputerDao.getInstance().findById(id);
        assertEquals(expected, actual);
    }

    @Test
    void update() {
        fail();
    }

    @Test
    void count() {
        final long count = ComputerDao.getInstance().count();
        assertEquals(TestDatabase.getInstance().findAllComputers().size(), count);
    }
}
