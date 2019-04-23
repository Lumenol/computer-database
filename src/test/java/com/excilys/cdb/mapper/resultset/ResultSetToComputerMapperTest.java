package com.excilys.cdb.mapper.resultset;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import com.excilys.cdb.dao.ConnectionProvider;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Computer.ComputerBuilder;

class ResultSetToComputerMapperTest {

    private static final String COLUMN_COMPANY_ID = "company_id";
    private static final String COLUMN_COMPANY_NAME = "company_name";
    private static final String COLUMN_DISCONTINUED = "discontinued";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_INTRODUCED = "introduced";
    private static final String COLUMN_NAME = "name";

    private ResultSet mockResultSet;

    @BeforeEach
    public void mockResultSet() {
	mockResultSet = Mockito.mock(ResultSet.class);
    };

    private static Stream<Arguments> provideComputer() {
	return Stream.of(Arguments.of(1l, "Le premier", null, null, 4l, "4eme corp"),
		Arguments.of(2l, "Le dexieme", LocalDate.of(2017, 10, 10), null, null, null),
		Arguments.of(3l, "Le 3eme", LocalDate.of(2016, 9, 9), LocalDate.of(2017, 10, 10), 5l, "Une autre corp"),
		Arguments.of(4l, "Le 4eme", null, LocalDate.of(2017, 10, 10), 6l, "La 6eme"));
    }

    @ParameterizedTest
    @MethodSource("provideComputer")
    public void testMap(long id, String name, LocalDate introduced, LocalDate discontinued, Long companyId,
	    String companyName) throws SQLException {
	ConnectionProvider instance = ConnectionProvider.getInstance();
	Mockito.when(mockResultSet.getLong(COLUMN_ID)).thenReturn(id);
	Mockito.when(mockResultSet.getString(COLUMN_NAME)).thenReturn(name);

	Date introducedSQL = Objects.nonNull(introduced) ? Date.valueOf(introduced) : null;
	Mockito.when(mockResultSet.getDate(COLUMN_INTRODUCED)).thenReturn(introducedSQL);

	Date discontinuedSQL = Objects.nonNull(discontinued) ? Date.valueOf(discontinued) : null;
	Mockito.when(mockResultSet.getDate(COLUMN_DISCONTINUED)).thenReturn(discontinuedSQL);

	long companyIdSQL = Objects.nonNull(companyId) ? companyId : 0;
	Mockito.when(mockResultSet.getLong(COLUMN_COMPANY_ID)).thenReturn(companyIdSQL);

	Mockito.when(mockResultSet.getString(COLUMN_COMPANY_NAME)).thenReturn(companyName);

	Computer computer = ResultSetToComputerMapper.getInstance().map(mockResultSet);

	ComputerBuilder computerBuilder = Computer.builder().id(id).name(name).introduced(introduced)
		.discontinued(discontinued);
	if (Objects.nonNull(companyId)) {
	    Company company = Company.builder().id(companyId).name(companyName).build();
	    computerBuilder.manufacturer(company);
	}
	Computer expectedComputer = computerBuilder.build();

	assertEquals(expectedComputer, computer);
    }
}