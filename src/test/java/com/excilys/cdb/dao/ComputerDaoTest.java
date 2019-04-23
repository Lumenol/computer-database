package com.excilys.cdb.dao;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ComputerDaoTest {
    
    @BeforeAll
    public static void initDatabase() throws SQLException, IOException {
	LoadDatabase.load();
    }
    
    @Test
    void testCount() {
	fail("Not yet implemented");
    }

}
