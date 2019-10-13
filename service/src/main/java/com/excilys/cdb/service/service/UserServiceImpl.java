package com.excilys.cdb.service.service;

import com.excilys.cdb.model.Role;
import com.excilys.cdb.model.User;
import com.excilys.cdb.persistence.dao.UserDAO;
import com.excilys.cdb.service.exception.UserServiceException;
import com.excilys.cdb.shared.logexception.LogAndWrapException;
import com.excilys.cdb.shared.pagination.Page;
import com.excilys.cdb.shared.validator.UserExistByLogin;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService, UserExistByLogin {

    private final UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    @LogAndWrapException(logger = UserService.class, exception = UserServiceException.class)
    @Transactional
    public void create(User user) {
        userDAO.create(user);
    }

    @Override
    @LogAndWrapException(logger = UserService.class, exception = UserServiceException.class)
    @Transactional
    public void updateRoles(Long id, Set<Role> roles) {
        userDAO.findById(id).ifPresent(user -> {
            user.setRoles(roles);
            userDAO.update(user);
        });
    }

    @Override
    @LogAndWrapException(logger = UserService.class, exception = UserServiceException.class)
    @Transactional
    public void deleteByLogin(String login) {
        userDAO.deleteByLogin(login);
    }

    @Override
    @LogAndWrapException(logger = UserService.class, exception = UserServiceException.class)
    @Transactional
    public void deleteById(long id) {
        userDAO.deleteById(id);
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

    @Override
    @LogAndWrapException(logger = UserService.class, exception = UserServiceException.class)
    public Optional<User> findById(long id) {
        return userDAO.findById(id);
    }

    @Override
    @LogAndWrapException(logger = UserService.class, exception = UserServiceException.class)
    public List<User> findAll(Page page) {
        return userDAO.findAll(page);
    }

    @Override
    @LogAndWrapException(logger = UserService.class, exception = UserServiceException.class)
    public long count() {
        return userDAO.count();
    }

    @Override
    @LogAndWrapException(logger = UserService.class, exception = UserServiceException.class)
    public List<User> findAll() {
        return userDAO.findAll();
    }
}
