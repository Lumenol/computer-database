package com.excilys.cdb.api.configuration;

import com.excilys.cdb.security.filter.TokenAuthenticationFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.session.SessionManagementFilter;

@Configuration
public class RestConfiguration extends WebSecurityConfigurerAdapter {


    private final TokenAuthenticationFilter tokenAuthenticationFilter;

    public RestConfiguration(TokenAuthenticationFilter tokenAuthenticationFilter) {
        this.tokenAuthenticationFilter = tokenAuthenticationFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().addFilterBefore(tokenAuthenticationFilter, SessionManagementFilter.class).sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
