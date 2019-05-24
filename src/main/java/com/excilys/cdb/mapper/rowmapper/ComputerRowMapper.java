package com.excilys.cdb.mapper.rowmapper;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Computer.ComputerBuilder;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;

@Component
public class ComputerRowMapper implements RowMapper<Computer> {

    private static final String COLUMN_COMPANY_ID = "company_id";
    private static final String COLUMN_COMPANY_NAME = "company_name";
    private static final String COLUMN_DISCONTINUED = "discontinued";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_INTRODUCED = "introduced";
    private static final String COLUMN_NAME = "name";

    @Override
    public Computer mapRow(ResultSet rs, int rowNum) throws SQLException {
        long id = rs.getLong(COLUMN_ID);
        String name = rs.getString(COLUMN_NAME);
        LocalDate introduced = null;
        final Date introducedSQL = rs.getDate(COLUMN_INTRODUCED);
        if (Objects.nonNull(introducedSQL)) {
            introduced = introducedSQL.toLocalDate();
        }
        LocalDate discontinued = null;
        final Date discontinuedSQL = rs.getDate(COLUMN_DISCONTINUED);
        if (Objects.nonNull(discontinuedSQL)) {
            discontinued = discontinuedSQL.toLocalDate();
        }
        ComputerBuilder computerBuilder = Computer.builder().id(id).name(name).introduced(introduced)
                .discontinued(discontinued);

        Long companyId = rs.getLong(COLUMN_COMPANY_ID);
        String compagnyName = rs.getString(COLUMN_COMPANY_NAME);
        if (Objects.nonNull(compagnyName)) {
            Company mannufacturer = Company.builder().id(companyId).name(compagnyName).build();
            computerBuilder.manufacturer(mannufacturer);
        }
        return computerBuilder.build();
    }

}
