package com.excilys.cdb.persistence.transaction;

import com.excilys.cdb.config.AppConfig;
import com.excilys.cdb.database.UTDatabase;
import com.excilys.cdb.exception.TransactionException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.ConnectionManager;
import com.excilys.cdb.persistence.dao.ComputerDAO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class TransactionTest {

    @Autowired
    private ConnectionManager connectionManager;
    @Autowired
    private ComputerDAO computerDAO;
    @Autowired
    private UTDatabase database;

    @Before
    public void setUp() throws Exception {
        database.reload();
    }

    @Test
    public void oneTransaction() throws SQLException {
        try (final Transaction transaction = connectionManager.getTransaction()) {
            final Connection connection = connectionManager.getConnection();
            final ArrayList<Connection> connections = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                connections.add(connectionManager.getConnection());
            }
            for (Connection c : connections) {
                assertSame(connection, c);
            }
            transaction.commit();
        }
    }

    @Test
    public void noAutoCommit() throws SQLException {
        final List<Computer> allComputers = database.findAllComputers();
        final long before = computerDAO.count();
        try (final Transaction transaction = connectionManager.getTransaction()) {
            computerDAO.deleteById(allComputers.get(0).getId());
            assertEquals(before - 1, computerDAO.count());
            computerDAO.deleteById(allComputers.get(1).getId());
            assertEquals(before - 2, computerDAO.count());
        }
        final long after = computerDAO.count();
        assertEquals(before, after);
    }

    @Test
    public void commit() throws SQLException {
        final List<Computer> allComputers = database.findAllComputers();
        final long before = computerDAO.count();
        try (final Transaction transaction = connectionManager.getTransaction()) {
            computerDAO.deleteById(allComputers.get(0).getId());
            assertEquals(before - 1, computerDAO.count());
            computerDAO.deleteById(allComputers.get(1).getId());
            assertEquals(before - 2, computerDAO.count());
            transaction.commit();
        }
        final long after = computerDAO.count();
        assertEquals(before - 2, after);
    }

    @Test
    public void halfRollback() throws SQLException {
        final List<Computer> allComputers = database.findAllComputers();
        final long before = computerDAO.count();
        try (final Transaction transaction = connectionManager.getTransaction()) {
            computerDAO.deleteById(allComputers.get(0).getId());
            assertEquals(before - 1, computerDAO.count());
            transaction.rollback();
            computerDAO.deleteById(allComputers.get(1).getId());
            assertEquals(before - 1, computerDAO.count());
            transaction.commit();
        }
        final long after = computerDAO.count();
        assertEquals(before - 1, after);
    }

    @Test
    public void commitAndRollback() throws InterruptedException {
        final Thread rollback = new Thread(() -> {
            try (final Transaction transaction = connectionManager.getTransaction()) {
                computerDAO.deleteById(1);
                computerDAO.deleteById(4);
                transaction.rollback();
            } catch (TransactionException e) {
                fail("Exception rollback");
            }
        });

        final Thread commit = new Thread(() -> {
            try (final Transaction transaction = connectionManager.getTransaction()) {
                computerDAO.deleteById(2);
                computerDAO.deleteById(6);
                transaction.commit();
            } catch (TransactionException e) {
                fail("Exception commit");
            }
        });

        rollback.start();
        commit.start();

        commit.join();
        rollback.join();

        final Optional<Computer> computer1 = computerDAO.findById(1);
        final Optional<Computer> computer2 = computerDAO.findById(2);
        final Optional<Computer> computer4 = computerDAO.findById(4);
        final Optional<Computer> computer6 = computerDAO.findById(6);

        assertTrue(computer1.isPresent());
        assertTrue(computer4.isPresent());

        assertFalse(computer2.isPresent());
        assertFalse(computer6.isPresent());
    }

    @Test
    public void connectionDifferent() throws InterruptedException {
        final ArrayList<Connection> connections1 = new ArrayList<>();
        final ArrayList<Connection> connections2 = new ArrayList<>();

        final Thread rollback = new Thread(() -> {
            try (final Transaction transaction = connectionManager.getTransaction()) {
                connections1.add(transaction.getConnection());
                connections1.add(transaction.getConnection());
                connections1.add(transaction.getConnection());
                connections1.add(transaction.getConnection());
            } catch (TransactionException e) {
                fail("Exception");
            }
        });

        final Thread commit = new Thread(() -> {
            try (final Transaction transaction = connectionManager.getTransaction()) {
                connections2.add(transaction.getConnection());
                connections2.add(transaction.getConnection());
                connections2.add(transaction.getConnection());
                connections2.add(transaction.getConnection());
            } catch (TransactionException e) {
                fail("Exception");
            }
        });

        rollback.start();
        commit.start();

        commit.join();
        rollback.join();

        for (Connection connection : connections1) {
            assertSame(connections1.get(0), connection);
        }
        for (Connection connection : connections2) {
            assertNotSame(connections1.get(0), connection);
        }
    }
}