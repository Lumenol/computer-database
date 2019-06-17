package com.excilys.cdb.api.controller;

import io.jsonwebtoken.Jwts;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.security.Key;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/login")
public class LoginController {
    private static final String BEARER = "Bearer";
    private static final long TOKEN_TIME_LIVE = 1800_000;
    private static final String SECURITY_TOKEN_TIME_TO_LIVE = "security.token.timeToLive";
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final Key key;
    private final long tokenTimeLive;

    public LoginController(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder, Key key, Environment environment) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.key = key;
        tokenTimeLive = Optional.ofNullable(environment.getProperty(SECURITY_TOKEN_TIME_TO_LIVE)).map(Long::valueOf).orElse(TOKEN_TIME_LIVE);
    }

    @GetMapping
    public ResponseEntity refreshToken(@ApiIgnore Authentication authentication) {
        if (Objects.nonNull(authentication)) {
            return createToken((UserDetails) authentication.getPrincipal());
        } else {
            throw new AccessDeniedException("Acces denied.");
        }
    }

    private ResponseEntity createToken(UserDetails userDetails) {
        final String token = Jwts.builder().setSubject(userDetails.getUsername()).setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis() + tokenTimeLive)).signWith(key).compact();
        return ResponseEntity.noContent().header(HttpHeaders.AUTHORIZATION, BEARER + " " + token).build();
    }

    @PostMapping
    public ResponseEntity login(@RequestBody Credentials credentials) {
        try {
            final UserDetails userDetails = userDetailsService.loadUserByUsername(credentials.getUsername());
            if (passwordEncoder.matches(credentials.getPassword(), userDetails.getPassword())) {
                return createToken(userDetails);
            } else {
                throw new BadCredentialsException("Bad credentials");
            }
        } catch (UsernameNotFoundException e) {
            throw new BadCredentialsException("Bad credentials");
        }
    }

    private static final class Credentials {
        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

}
