package com.excilys.cdb.config;

import java.util.TimeZone;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.excilys.cdb.controller.Controller;
import com.excilys.cdb.mapper.resultset.ResultSetToCompanyMapper;
import com.excilys.cdb.mapper.resultset.ResultSetToListMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.ui.Cli;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan(basePackages = "com.excilys.cdb")
public class AppConfig {

    @Bean
    public DataSource dataSource() {
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
    public Cli cli(Controller controller) {
	return new Cli(controller, System.in, System.out);
    }

}
