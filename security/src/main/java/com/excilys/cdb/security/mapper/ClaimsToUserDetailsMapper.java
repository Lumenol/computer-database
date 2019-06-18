package com.excilys.cdb.security.mapper;

import com.excilys.cdb.shared.mapper.Mapper;
import io.jsonwebtoken.Claims;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;

@Component
public class ClaimsToUserDetailsMapper implements Mapper<Claims, UserDetails> {
    @Override
    public UserDetails map(Claims claims) {
        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                final Object authorisation = claims.get(HttpHeaders.AUTHORIZATION);
                if (authorisation instanceof Collection) {
                    return ((Collection<?>) authorisation).stream().map(Object::toString)
                } else {
                    return Collections.emptyList();
                }
            }

            @Override
            public String getPassword() {
                return null;
            }

            @Override
            public String getUsername() {
                return claims.getSubject();
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }
        };
    }
}
