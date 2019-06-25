package com.excilys.cdb.persistence.dao;

import com.excilys.cdb.model.User;

import java.util.Optional;

public interface UserDAO {

    void create(User user);

    void update(User user);

    void deleteByLogin(String login);

    boolean existByLogin(String login);

    Optional<User> findByLogin(String login);

    void deleteById(long id);

    Optional<User> findById(long id);
}
