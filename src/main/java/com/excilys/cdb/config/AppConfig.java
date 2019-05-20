package com.excilys.cdb.config;

import java.util.TimeZone;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.excilys.cdb.controller.cli.Controller;
import com.excilys.cdb.controller.web.pagination.Pagination;
import com.excilys.cdb.controller.web.pagination.PaginationParameters;
import com.excilys.cdb.controller.web.sorting.Sorting;
import com.excilys.cdb.controller.web.sorting.SortingParameters;
import com.excilys.cdb.ui.Cli;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan(basePackages = "com.excilys.cdb", excludeFilters = @ComponentScan.Filter(classes = EnableWebMvc.class))
@EnableTransactionManagement
public class AppConfig {

    @Bean(destroyMethod = "close")
    public HikariDataSource dataSource(HikariConfig configuration) {
	TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	return new HikariDataSource(configuration);
    }

    @Bean
    public HikariConfig hikariConfig() {
	return new HikariConfig("/datasource.properties");
    }

    @Bean
    public Cli cli(Controller controller) {
	return new Cli(controller, System.in, System.out);
    }

    @Bean
    public PaginationParameters paginationParameters() {
	final int MIN_PAGE_SIZE = 10;
	final String PARAMETER_NEXT = "next";
	final String PARAMETER_PAGE = "page";
	final String PARAMETER_PAGES = "pages";
	final String PARAMETER_PREVIOUS = "previous";
	final String PARAMETER_SIZE = "size";
	final String PARAMETER_ORDER_BY = "order-by";
	final String PARAMETER_MEANING = "meaning";

	return new PaginationParameters(MIN_PAGE_SIZE, PARAMETER_SIZE, PARAMETER_PAGE, PARAMETER_PREVIOUS,
		PARAMETER_NEXT, PARAMETER_PAGES, PARAMETER_ORDER_BY, PARAMETER_MEANING);
    }

    @Bean
    public Pagination pagination(PaginationParameters parameters) {
	return new Pagination(parameters);
    }

    @Bean
    public SortingParameters sortingParameters() {
	final String PARAMETER_ORDER_BY = "order-by";
	final String PARAMETER_MEANING = "meaning";
	final String PARAMETER_ORDER_BY_UTILS = "order-utils";

	return new SortingParameters(PARAMETER_ORDER_BY, PARAMETER_MEANING, PARAMETER_ORDER_BY_UTILS);
    }

    @Bean
    public Sorting sorting(SortingParameters parameters) {
	return new Sorting(parameters);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
	return new JdbcTemplate(dataSource);
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
	return new DataSourceTransactionManager(dataSource);
    }

}
