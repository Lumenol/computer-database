package com.excilys.cdb.service.service;

import com.excilys.cdb.model.User;
import com.excilys.cdb.persistence.dao.UserDAO;
import com.excilys.cdb.service.exception.UserServiceException;
import com.excilys.cdb.shared.logexception.LogAndWrapException;
import com.excilys.cdb.shared.mapper.Mapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserDAO userDAO;
    private final Mapper<User, UserDetails> userToUserDetailsMapper;

    public UserServiceImpl(UserDAO userDAO, Mapper<User, UserDetails> userToUserDetailsMapper) {
        this.userDAO = userDAO;
        this.userToUserDetailsMapper = userToUserDetailsMapper;
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDAO.findByLogin(username).map(userToUserDetailsMapper::map).orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
