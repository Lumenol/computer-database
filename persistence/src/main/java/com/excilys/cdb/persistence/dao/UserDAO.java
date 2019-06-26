package com.excilys.cdb.persistence.dao;

import com.excilys.cdb.model.User;
import com.excilys.cdb.shared.pagination.Page;

import java.util.List;
import java.util.Optional;

public interface UserDAO {

    void create(User user);

    void update(User user);

    void deleteByLogin(String login);

    boolean existByLogin(String login);

    Optional<User> findByLogin(String login);

    void deleteById(long id);

    Optional<User> findById(long id);

    List<User> findAll();

    List<User> findAll(Page page);

    long count();
}
