package com.excilys.cdb.security.mapper;

import com.excilys.cdb.shared.mapper.Mapper;
import io.jsonwebtoken.Claims;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class ClaimsToUserDetailsMapper implements Mapper<Claims, UserDetails> {
    @Override
    public UserDetails map(Claims claims) {
        return new MyUserDetails(claims);
    }

    private static class MyUserDetails implements UserDetails {
        private static final long serialVersionUID = 2670639839044154452L;
        private final Claims claims;

        public MyUserDetails(Claims claims) {
            this.claims = claims;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            final ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
            for (Object o : (Collection) claims.get(HttpHeaders.AUTHORIZATION)) {
                authorities.add(new SimpleGrantedAuthority(o.toString()));
            }
            return authorities;
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
    }
}
