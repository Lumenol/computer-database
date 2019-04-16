package com.metier.dao;

import java.util.List;
import java.util.Optional;

import com.metier.entite.Compagny;

public interface CompagnyDAO {
	List<Compagny> findAll();
	Optional<Compagny> findById(long id);
}
