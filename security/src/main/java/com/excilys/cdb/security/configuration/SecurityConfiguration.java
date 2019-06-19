package com.excilys.cdb.security.configuration;

import com.excilys.cdb.model.Role;
import com.excilys.cdb.model.User;
import com.excilys.cdb.service.service.UserService;
import com.excilys.cdb.shared.mapper.Mapper;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.Key;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@ComponentScan(basePackages = {"com.excilys.cdb.security.mapper", "com.excilys.cdb.security.filter"})
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

    @Bean
    public static Key key() {
        return Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    @Bean
    public RoleHierarchy roleHierarchy(Mapper<Role, GrantedAuthority> roleGrantedAuthorityMapper) {
        RoleHierarchyImpl r = new RoleHierarchyImpl();
        final List<Role> roles = Arrays.asList(Role.ADMIN, Role.USER);
        final String hierarchy = String.join(" > ", roles.stream().map(roleGrantedAuthorityMapper::map).map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        r.setHierarchy(hierarchy);
        return r;
    }
}
