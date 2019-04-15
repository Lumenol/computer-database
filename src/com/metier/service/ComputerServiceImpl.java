package com.metier.service;

import java.util.List;

import com.metier.dao.ComputerDAO;
import com.metier.dto.ComputerDTO;
import com.metier.dto.CreateComputerDTO;
import com.metier.dto.UpdateComputerDTO;
import com.metier.validator.CreateComputerValidator;
import com.metier.validator.UpdateComputerValidator;

public class ComputerServiceImpl implements ComputerService {

	private final ComputerDAO computerDAO;
	private final CreateComputerValidator createComputerValidator;
	private final UpdateComputerValidator updateComputerValidator;

	public ComputerServiceImpl(ComputerDAO computerDAO, CreateComputerValidator createComputerValidator,
			UpdateComputerValidator updateComputerValidator) {
		super();
		this.computerDAO = computerDAO;
		this.createComputerValidator = createComputerValidator;
		this.updateComputerValidator = updateComputerValidator;
	}

	@Override
	public List<ComputerDTO> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ComputerDTO findById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(UpdateComputerDTO computer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void create(CreateComputerDTO computer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub

	}

}
