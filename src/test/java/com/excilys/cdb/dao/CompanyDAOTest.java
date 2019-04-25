package com.excilys.cdb.dao;

import com.excilys.cdb.TestDatabase;
import com.excilys.cdb.model.Company;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CompanyDAOTest {

    @BeforeEach
    public void loadEnttries() throws IOException, SQLException {
	TestDatabase.getInstance().reload();
    }

    private static Stream<Long> provideCompanyId() {
	final Stream.Builder<Long> builder = Stream.builder();
	final List<Company> companies = TestDatabase.getInstance().findAllCompanies();
	final Company lastCcompanies = companies.get(companies.size() - 1);
	for (long i = -5; i < lastCcompanies.getId() + 5; i++) {
	    builder.add(i);
	}
	return builder.build();
    }

    @ParameterizedTest
    @MethodSource("provideCompanyId")
    void findById(long id) {
	final Optional<Company> expected = Optional.ofNullable(TestDatabase.getInstance().findCompanyById(id));
	final Optional<Company> actual = CompanyDAO.getInstance().findById(id);
	assertEquals(expected, actual);
    }

    private static Stream<Arguments> provideOffsetLimit() {
	return Stream.of(Arguments.of(0, 30), Arguments.of(0, 5), Arguments.of(5, 5), Arguments.of(13, 25));
    }

    @ParameterizedTest
    @MethodSource("provideOffsetLimit")
    void findAll(long offset, long limit) {
	final List<Company> actual = CompanyDAO.getInstance().findAll(offset, limit);
	final List<Company> expected = TestDatabase.getInstance().findAllCompanies(offset, limit);
	assertEquals(expected, actual);
    }

    @Test
    void count() {
	final long count = CompanyDAO.getInstance().count();
	assertEquals(TestDatabase.getInstance().findAllCompanies().size(), count);
    }
}
