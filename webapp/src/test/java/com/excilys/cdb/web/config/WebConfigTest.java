package com.excilys.cdb.web.config;

import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
@Import(WebConfig.class)
public class WebConfigTest {
    @Bean
    @Primary
    public ComputerService computerService() {
        return Mockito.mock(ComputerService.class);
    }

    @Bean
    @Primary
    public CompanyService companyService() {
        return Mockito.mock(CompanyService.class);
    }

    @Bean
    @Primary
    /*
    Spring initialise toutes les beans même inutile comme les DAOs et elles ont besoins d'un JdbcTemplate qui a besoin d'une DataSource.
     */
    public DataSource dataSource() {
        return Mockito.mock(DataSource.class);
    }
}
