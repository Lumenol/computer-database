package com.excilys.cdb.service.service;

import com.excilys.cdb.model.Role;
import com.excilys.cdb.model.User;
import com.excilys.cdb.shared.pagination.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserService {
    void create(User user);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    void updateRoles(Long id, Set<Role> roles);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    void deleteByLogin(String login);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    void deleteById(long id);

    boolean existByLogin(String login);

    Optional<User> findByLogin(String login);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    Optional<User> findById(long id);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    List<User> findAll();

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    List<User> findAll(Page page);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    long count();
}
