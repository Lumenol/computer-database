package com.excilys.cdb.mapper.resultset;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import com.excilys.cdb.model.Company;

class ResultSetToCompanyMapperTest {

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";

    private ResultSet mockResultSet;

    @BeforeEach
    public void mockResultSet() {
	mockResultSet = Mockito.mock(ResultSet.class);
    };

    private static Stream<Arguments> provideCompany() {
	return Stream.of(Arguments.of(1l, "La premiere"), Arguments.of(2l, "Le dexieme"), Arguments.of(3l, "Le3eme"),
		Arguments.of(4l, ""));
    }

    @ParameterizedTest
    @MethodSource("provideCompany")
    public void testMap(long id, String name) throws SQLException {
	Mockito.when(mockResultSet.getLong(COLUMN_ID)).thenReturn(id);
	Mockito.when(mockResultSet.getString(COLUMN_NAME)).thenReturn(name);

	Company company = ResultSetToCompanyMapper.getInstance().map(mockResultSet);
	Company expectedCompany = Company.builder().id(id).name(name).build();

	assertEquals(expectedCompany, company);
    }

}
