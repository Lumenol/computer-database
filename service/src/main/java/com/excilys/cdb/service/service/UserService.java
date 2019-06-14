package com.excilys.cdb.service.service;

import com.excilys.cdb.model.User;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Optional;

public interface UserService {
    void create(User user);

    @PreAuthorize("isAuthenticated()")
    void deleteByLogin(String login);

    @PreAuthorize("isAuthenticated()")
    void deleteById(long id);

    boolean existByLogin(String login);

    Optional<User> findByLogin(String login);

    @PreAuthorize("isAuthenticated()")
    Optional<User> findById(long id);
}
