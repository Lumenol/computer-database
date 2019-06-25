package com.excilys.cdb.persistence.dao;

import com.excilys.cdb.model.Role;
import com.excilys.cdb.model.User;
import com.excilys.cdb.persistence.configuration.PersistenceConfigurationTest;
import com.excilys.cdb.persistence.exception.UserDAOException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = PersistenceConfigurationTest.class)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, statements = "DELETE FROM user;")
class UserDAOTest {

    private UserDAO userDAO;

    @Autowired
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Test
    public void createSucess() {
        final String login = "login";
        final User user = User.builder().login(login).password("password").build();
        userDAO.create(user);
        assertTrue(userDAO.existByLogin(login));
    }

    @Test
    public void createFailBecauseLoginAlreadyExists() {
        final String login = "login";
        final User user = User.builder().login(login).password("password").build();
        userDAO.create(user);

        assertTrue(userDAO.existByLogin(login));
        final User user2 = User.builder().login(login).password("password2").build();
        assertThrows(UserDAOException.class, () -> userDAO.create(user2));
    }

    @Test
    public void deleteByLogin() {
        final String login = "login";
        final User user = User.builder().login(login).password("password").build();
        userDAO.create(user);
        assertTrue(userDAO.existByLogin(login));
        userDAO.deleteByLogin(login);
        assertFalse(userDAO.existByLogin(login));
    }

    @Test
    public void existByLogin() {
        final String login = "login";
        assertFalse(userDAO.existByLogin(login));
        final User user = User.builder().login(login).password("password").build();
        userDAO.create(user);
        assertTrue(userDAO.existByLogin(login));
    }

    @Test
    public void findByLogin() {
        final String login = "log";
        final User user = User.builder().login(login).password("word").build();
        userDAO.create(user);
        final Optional<User> byLogin = userDAO.findByLogin(login);
        assertEquals(byLogin.get(), user);
    }

    @Test
    public void findById() {
        final User user = User.builder().login("log").password("word").build();
        userDAO.create(user);
        final Optional<User> byLogin = userDAO.findById(user.getId());
        assertEquals(byLogin.get(), user);
    }

    @Test
    public void deleteById() {
        final User user = User.builder().login("login").password("password").build();
        userDAO.create(user);
        final Long id = user.getId();
        final Optional<User> avant = userDAO.findById(id);
        userDAO.deleteById(id);
        final Optional<User> apres = userDAO.findById(id);
        assertTrue(avant.isPresent());
        assertFalse(apres.isPresent());
    }

    @Test
    public void update() {
        final User cree = User.builder().login("usersueruser").password("password").build();
        userDAO.create(cree);
        final User before = userDAO.findById(cree.getId()).get();
        before.getRoles().add(Role.ADMIN);
        userDAO.update(before);
        final User actual = userDAO.findById(cree.getId()).get();
        assertEquals(before, actual);
    }

}