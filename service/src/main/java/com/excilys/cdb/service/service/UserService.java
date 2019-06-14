package com.excilys.cdb.service.service;

import com.excilys.cdb.model.User;

import java.util.Optional;

public interface UserService {
    void create(User user);

    void deleteByLogin(String login);

    void deleteById(long id);

    boolean existByLogin(String login);

    Optional<User> findByLogin(String login);

    Optional<User> findById(long id);
}
