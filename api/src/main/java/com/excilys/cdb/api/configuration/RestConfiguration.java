package com.excilys.cdb.api.configuration;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.session.SessionManagementFilter;

import java.security.Key;

@Configuration
public class RestConfiguration extends WebSecurityConfigurerAdapter {


    private final TokenAuthenticationFilter tokenAuthenticationFilter;

    public RestConfiguration(TokenAuthenticationFilter tokenAuthenticationFilter) {
        this.tokenAuthenticationFilter = tokenAuthenticationFilter;
    }

    @Bean
    public static TokenAuthenticationFilter tokenAuthenticationFilter(UserDetailsService userDetailsService, Key key) {
        return new TokenAuthenticationFilter(userDetailsService, key);
    }

    @Bean
    public static Key key() {
        return Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().addFilterBefore(tokenAuthenticationFilter, SessionManagementFilter.class).sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

}
