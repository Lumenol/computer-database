package com.metier.dao;

import java.util.List;
import java.util.Optional;

import com.metier.entite.Company;

public interface CompanyDAO {
	List<Company> findAll();
	Optional<Company> findById(long id);
}
