package com.excilys.cdb.service.service;

import com.excilys.cdb.model.User;
import com.excilys.cdb.persistence.dao.UserDAO;
import com.excilys.cdb.service.exception.UserServiceException;
import com.excilys.cdb.shared.logexception.LogAndWrapException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    @LogAndWrapException(logger = UserService.class, exception = UserServiceException.class)
    @Transactional
    public long create(User user) {
        return userDAO.create(user);
    }

    @Override
    @LogAndWrapException(logger = UserService.class, exception = UserServiceException.class)
    @Transactional
    public void deleteByLogin(String login) {
        userDAO.deleteByLogin(login);
    }

    @Override
    @LogAndWrapException(logger = UserService.class, exception = UserServiceException.class)
    public boolean existByLogin(String login) {
        return userDAO.existByLogin(login);
    }

    @Override
    @LogAndWrapException(logger = UserService.class, exception = UserServiceException.class)
    public Optional<User> findByLogin(String login) {
        return userDAO.findByLogin(login);
    }
}
