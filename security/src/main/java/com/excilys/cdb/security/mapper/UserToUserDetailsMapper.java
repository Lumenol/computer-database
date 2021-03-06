package com.excilys.cdb.security.mapper;

import com.excilys.cdb.model.Role;
import com.excilys.cdb.model.User;
import com.excilys.cdb.shared.mapper.Mapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class UserToUserDetailsMapper implements Mapper<User, UserDetails> {

    private final Mapper<Role, GrantedAuthority> roleGrantedAuthorityMapper;

    public UserToUserDetailsMapper(Mapper<Role, GrantedAuthority> roleGrantedAuthorityMapper) {
        this.roleGrantedAuthorityMapper = roleGrantedAuthorityMapper;
    }

    @Override
    public UserDetails map(User user) {
        return new UserDetails() {

            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return user.getRoles().stream().map(roleGrantedAuthorityMapper::map).collect(Collectors.toList());
            }

            @Override
            public String getPassword() {
                return user.getPassword();
            }

            @Override
            public String getUsername() {
                return user.getLogin();
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
