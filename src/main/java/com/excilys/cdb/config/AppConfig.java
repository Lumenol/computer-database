package com.excilys.cdb.config;

import com.excilys.cdb.controller.Controller;
import com.excilys.cdb.mapper.resultset.ResultSetToCompanyMapper;
import com.excilys.cdb.mapper.resultset.ResultSetToComputerMapper;
import com.excilys.cdb.mapper.resultset.ResultSetToListMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.ui.Cli;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.TimeZone;

@Configuration
@ComponentScan(basePackages = "com.excilys.cdb")
public class AppConfig {

    @Bean(destroyMethod = "close")
    public HikariDataSource dataSource() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        HikariConfig config = new HikariConfig("/datasource.properties");
        return new HikariDataSource(config);
    }

    @Bean
    public ResultSetToListMapper<Company> companyResultSetToListMapper(
            ResultSetToCompanyMapper resultSetToCompanyMapper) {
        return new ResultSetToListMapper<>(resultSetToCompanyMapper);
    }

    @Bean
    public ResultSetToListMapper<Computer> computerResultSetToListMapper(
            ResultSetToComputerMapper resultSetToComputerMapper) {
        return new ResultSetToListMapper<>(resultSetToComputerMapper);
    }

    @Bean
    public Cli cli(Controller controller) {
        return new Cli(controller, System.in, System.out);
    }

}
