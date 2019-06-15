package com.excilys.cdb.webapp.configuration;

import com.excilys.cdb.webapp.pagination.Paging;
import com.excilys.cdb.webapp.pagination.PagingParameters;
import com.excilys.cdb.webapp.sorting.Sorting;
import com.excilys.cdb.webapp.sorting.SortingParameters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@ComponentScan(basePackages = {
        "com.excilys.cdb.webapp.mapper"}, excludeFilters = @ComponentScan.Filter(Configuration.class))
public class WebConfiguration {

    @Bean
    public PagingParameters paginationParameters() {
        final String PARAMETER_NEXT = "next";
        final String PARAMETER_PAGE = "page";
        final String PARAMETER_PAGES = "pages";
        final String PARAMETER_PREVIOUS = "previous";
        final String PARAMETER_SIZE = "size";

        return new PagingParameters(PARAMETER_SIZE, PARAMETER_PAGE, PARAMETER_PREVIOUS, PARAMETER_NEXT,
                PARAMETER_PAGES);
    }

    @Bean
    public Paging paging(PagingParameters parameters) {
        return new Paging(parameters);
    }

    @Bean
    public SortingParameters sortingParameters() {
        final String PARAMETER_ORDER_BY = "order-by";
        final String PARAMETER_DIRECTION = "direction";
        final String PARAMETER_ORDER_BY_UTILS = "order-utils";

        return new SortingParameters(PARAMETER_ORDER_BY, PARAMETER_DIRECTION, PARAMETER_ORDER_BY_UTILS);
    }

    @Bean
    public Sorting sorting(SortingParameters parameters) {
        return new Sorting(parameters);
    }

    @Configuration
    @Import(com.excilys.cdb.security.configuration.SecurityConfiguration.class)
    public static class SecurityConfiguration extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests().and().formLogin().loginPage("/login").permitAll().and().logout().permitAll();
        }
    }
}
