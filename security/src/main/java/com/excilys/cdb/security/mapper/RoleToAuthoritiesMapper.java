package com.excilys.cdb.security.mapper;

import com.excilys.cdb.model.Role;
import com.excilys.cdb.shared.mapper.Mapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class RoleToAuthoritiesMapper implements Mapper<Role, GrantedAuthority> {
    private static final String ROLE_PREFIX = "ROLE";

    @Override
    public GrantedAuthority map(Role role) {
        return new SimpleGrantedAuthority(ROLE_PREFIX + "_" + role.name());
    }
}
