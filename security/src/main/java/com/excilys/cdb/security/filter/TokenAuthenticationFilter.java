package com.excilys.cdb.security.filter;

import com.excilys.cdb.shared.mapper.Mapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Key;
import java.util.Optional;

@Component
public class TokenAuthenticationFilter extends GenericFilterBean {

    private static final String BEARER = "Bearer";
    private final Key key;

    private final Mapper<Claims, UserDetails> claimsUserDetailsMapper;

    public TokenAuthenticationFilter(UserDetailsService userDetailsService, Key key, Mapper<Claims, UserDetails> claimsUserDetailsMapper) {
        this.key = key;
        this.claimsUserDetailsMapper = claimsUserDetailsMapper;
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
        final HttpServletRequest httpRequest = (HttpServletRequest) request;
        final Optional<String> token = Optional.ofNullable(httpRequest.getHeader(HttpHeaders.AUTHORIZATION)).filter(h -> h.startsWith(BEARER)).map(h -> h.substring(BEARER.length()).trim());
        try {
            token.map(s -> Jwts.parser().setSigningKey(key).parseClaimsJws(s)).map(Jwt::getBody).map(claimsUserDetailsMapper::map).map(u -> new UsernamePasswordAuthenticationToken(u, null, u.getAuthorities())).ifPresent(SecurityContextHolder.getContext()::setAuthentication);
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException | ExpiredJwtException | IllegalArgumentException e) {
        }
        chain.doFilter(request, response);
    }

}