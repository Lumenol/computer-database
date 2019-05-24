package com.excilys.cdb.persistence.dao;

import com.excilys.cdb.config.TestConfig;
import com.excilys.cdb.database.UTDatabase;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.pagination.Page;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

@RunWith(JUnitParamsRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class CompanyDAOTest {

    @ClassRule
    public static final SpringClassRule springClassRule = new SpringClassRule();
    @Rule
    public final SpringMethodRule springMethodRule = new SpringMethodRule();

    @Autowired
    private UTDatabase database;
    @Autowired
    private CompanyDAO companyDAO;

    public Object[] provideCompanyId() {
        final Stream.Builder<Long> builder = Stream.builder();
        for (long i = 0; i < 25; i++) {
            builder.add(i);
        }
        return builder.build().toArray();
    }

    public Object[] providePageLimit() {
        return new Object[][]{{1, 30}, {1, 5}, {3, 5}, {2, 10}};
    }

    @Before
    public void loadEnttries() throws IOException, SQLException {
        database.reload();
    }

    @Test
    @Parameters(method = "provideCompanyId")
    public void findById(long id) {
        final Optional<Company> expected = Optional.ofNullable(database.findCompanyById(id));
        final Optional<Company> actual = companyDAO.findById(id);
        assertEquals(expected, actual);
    }

    @Test
    @Parameters(method = "providePageLimit")
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
}
