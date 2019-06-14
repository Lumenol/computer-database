package com.excilys.cdb.security.configuration;

import com.excilys.cdb.model.User;
import com.excilys.cdb.service.service.UserService;
import com.excilys.cdb.shared.mapper.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@ComponentScan(basePackages = "com.excilys.cdb.security.mapper")
public class SecurityConfiguration {

    @Bean
    public static UserDetailsService userDetailsService(UserService userService,
                                                        Mapper<User, UserDetails> userUserDetailsMapper) {
        return username -> userService.findByLogin(username).map(userUserDetailsMapper::map)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
