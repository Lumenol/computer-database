package com.excilys.cdb.dao;

import com.excilys.cdb.database.UTDatabase;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.ConnectionManager;
import com.excilys.cdb.persistence.dao.ComputerDAO;
import com.excilys.cdb.persistence.transaction.Transaction;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class TransactionTest {

    private static final ConnectionManager CONNECTION_MANAGER = ConnectionManager.getInstance();

    @Before
    public void setUp() throws Exception {
        UTDatabase.getInstance().reload();
    }

    @Test
    public void oneTransaction() throws SQLException {
        try (final Transaction transaction = CONNECTION_MANAGER.getTransaction()) {
            final Connection connection = CONNECTION_MANAGER.getConnection();
            final ArrayList<Connection> connections = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                connections.add(CONNECTION_MANAGER.getConnection());
            }
            for (Connection c : connections) {
                assertSame(connection, c);
            }
            transaction.commit();
        }
    }

    @Test
    public void noAutoCommit() throws SQLException {
        final ComputerDAO computerDAO = ComputerDAO.getInstance();
        final List<Computer> allComputers = UTDatabase.getInstance().findAllComputers();
        final long before = computerDAO.count();
        try (final Transaction transaction = CONNECTION_MANAGER.getTransaction()) {
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
        final ComputerDAO computerDAO = ComputerDAO.getInstance();
        final List<Computer> allComputers = UTDatabase.getInstance().findAllComputers();
        final long before = computerDAO.count();
        try (final Transaction transaction = CONNECTION_MANAGER.getTransaction()) {
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
        final ComputerDAO computerDAO = ComputerDAO.getInstance();
        final List<Computer> allComputers = UTDatabase.getInstance().findAllComputers();
        final long before = computerDAO.count();
        try (final Transaction transaction = CONNECTION_MANAGER.getTransaction()) {
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
        final ComputerDAO computerDAO = ComputerDAO.getInstance();

        final Thread rollback = new Thread(() -> {
            try (final Transaction transaction = CONNECTION_MANAGER.getTransaction()) {
                computerDAO.deleteById(1);
                computerDAO.deleteById(4);
                transaction.rollback();
            } catch (SQLException e) {
                fail("Exception rollback");
            }
        });

        final Thread commit = new Thread(() -> {
            try (final Transaction transaction = CONNECTION_MANAGER.getTransaction()) {
                computerDAO.deleteById(2);
                computerDAO.deleteById(6);
                transaction.commit();
            } catch (SQLException e) {
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
            try (final Transaction transaction = CONNECTION_MANAGER.getTransaction()) {
                connections1.add(transaction.getConnection());
                connections1.add(transaction.getConnection());
                connections1.add(transaction.getConnection());
                connections1.add(transaction.getConnection());
            } catch (SQLException e) {
                fail("Exception");
            }
        });

        final Thread commit = new Thread(() -> {
            try (final Transaction transaction = CONNECTION_MANAGER.getTransaction()) {
                connections2.add(transaction.getConnection());
                connections2.add(transaction.getConnection());
                connections2.add(transaction.getConnection());
                connections2.add(transaction.getConnection());
            } catch (SQLException e) {
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