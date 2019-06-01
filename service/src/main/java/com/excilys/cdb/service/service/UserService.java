package com.excilys.cdb.service.service;

import com.excilys.cdb.model.User;

import java.util.Optional;

public interface UserService {
    long create(User user);

    void deleteByLogin(String login);

    boolean existByLogin(String login);

    Optional<User> findByLogin(String login);
}
