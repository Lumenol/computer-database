package com.excilys.cdb.security.configuration;

import com.excilys.cdb.model.Role;
import com.excilys.cdb.model.User;
import com.excilys.cdb.service.service.UserService;
import com.excilys.cdb.shared.mapper.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@ComponentScan(basePackages = "com.excilys.cdb.security.mapper")
public class SecurityConfiguration {

    private static final String ROLE_PREFIX = "ROLE_";

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

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl r = new RoleHierarchyImpl();
        final List<Role> roles = Arrays.asList(Role.ADMIN, Role.USER);
        final String hierarchy = String.join(" > ", roles.stream().map(role -> ROLE_PREFIX + role.name()).collect(Collectors.toList()));
        r.setHierarchy(hierarchy);
        return r;
    }
}
