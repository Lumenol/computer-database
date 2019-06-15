package com.excilys.cdb.persistence.dao;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.configuration.PersistenceConfigurationTest;
import com.excilys.cdb.shared.pagination.Page;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = PersistenceConfigurationTest.class)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "entries.sql")
public class CompanyDAOTest {
    private UTDatabase database = UTDatabase.getInstance();
    private CompanyDAO companyDAO;

    public static Stream<Long> provideCompanyId() {
        final Stream.Builder<Long> builder = Stream.builder();
        for (long i = 0; i < 25; i++) {
            builder.add(i);
        }
        return builder.build();
    }

    public static Stream<Arguments> providePageLimit() {
        return Stream.of(Arguments.of(1, 30), Arguments.of(1, 5), Arguments.of(3, 5), Arguments.of(2, 10));
    }

    @Autowired
    public void setCompanyDAO(CompanyDAO companyDAO) {
        this.companyDAO = companyDAO;
    }

    @ParameterizedTest
    @MethodSource("provideCompanyId")
    public void findById(long id) {
        final Optional<Company> expected = Optional.ofNullable(database.findCompanyById(id));
        final Optional<Company> actual = companyDAO.findById(id);
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("providePageLimit")
    public void findAll(long index, long limit) {
        final Page page = Page.builder().page(index).size(limit).build();
        final List<Company> actual = companyDAO.findAll(page);
        final List<Company> expected = database.findAllCompanies(index, limit);
        assertEquals(expected, actual);
    }

    @Test
    public void findAll() {
        final List<Company> actual = companyDAO.findAll();
        final List<Company> expected = database.findAllCompanies();
        assertEquals(expected, actual);
    }

    @Test
    public void count() {
        final long count = companyDAO.count();
        assertEquals(database.findAllCompanies().size(), count);
    }

    @Test
    public void deleteById() {
        final int id = 7;
        final Optional<Company> le5avant = companyDAO.findById(id);
        companyDAO.deleteById(id);
        final Optional<Company> le5apres = companyDAO.findById(id);
        assertTrue(le5avant.isPresent());
        assertFalse(le5apres.isPresent());
    }

    @ParameterizedTest
    @MethodSource("provideCompanyId")
    public void exist(long id) {
        final boolean expected = Optional.ofNullable(database.findCompanyById(id)).isPresent();
        final boolean actual = companyDAO.exist(id);
        assertEquals(expected, actual);
    }

}
