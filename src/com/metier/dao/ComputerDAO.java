package com.metier.dao;

import java.util.List;
import java.util.Optional;

import com.metier.entite.Computer;

public interface ComputerDAO {
	List<Computer> findAll();
	Optional<Computer> findById(long id);
	long create(Computer computer);
	void update(Computer computer);
	void deleteById(long id);
}
