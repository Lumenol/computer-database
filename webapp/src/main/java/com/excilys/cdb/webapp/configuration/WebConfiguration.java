package com.excilys.cdb.webapp.configuration;

import com.excilys.cdb.shared.pagination.PagingParameters;
import com.excilys.cdb.shared.pagination.SortingParameters;
import com.excilys.cdb.webapp.paging.Paging;
import com.excilys.cdb.webapp.paging.Sorting;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebConfiguration {

    @Bean
    public Paging paging(PagingParameters parameters) {
        return new Paging(parameters);
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
