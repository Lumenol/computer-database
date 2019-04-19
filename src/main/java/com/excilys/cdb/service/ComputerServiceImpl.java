package com.excilys.cdb.service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.excilys.cdb.dao.ComputerDAO;
import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.dto.CreateComputerDTO;
import com.excilys.cdb.dto.UpdateComputerDTO;
import com.excilys.cdb.exception.ComputerNotFoundException;
import com.excilys.cdb.exception.ValidatorException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.validator.Validator;

import all.ValidatorFactory;

public class ComputerServiceImpl implements ComputerService {

    private final ComputerDAO computerDAO;
    private final Function<Computer, ComputerDTO> computerToComputerDTO;
    private final Function<CreateComputerDTO, Computer> createComputerDTOToComputer;
    private final ValidatorFactory<CreateComputerDTO> createComputerValidatorFactory;
    private final Function<UpdateComputerDTO, Computer> updateComputerDTOToComputer;
    private final ValidatorFactory<UpdateComputerDTO> updateComputerValidatorFactory;

    public ComputerServiceImpl(ComputerDAO computerDAO,
	    ValidatorFactory<CreateComputerDTO> createComputerValidatorFactory,
	    ValidatorFactory<UpdateComputerDTO> updateComputerValidatorFactory,
	    Function<Computer, ComputerDTO> computerToComputerDTO,
	    Function<UpdateComputerDTO, Computer> updateComputerDTOToComputer,
	    Function<CreateComputerDTO, Computer> createComputerDTOToComputer) {
	super();
	this.computerDAO = computerDAO;
	this.createComputerValidatorFactory = createComputerValidatorFactory;
	this.updateComputerValidatorFactory = updateComputerValidatorFactory;
	this.computerToComputerDTO = computerToComputerDTO;
	this.updateComputerDTOToComputer = updateComputerDTOToComputer;
	this.createComputerDTOToComputer = createComputerDTOToComputer;
    }

    /**
     * @throws {@link ValidatorException}
     */
    @Override
    public void create(CreateComputerDTO computer) {
	Validator<CreateComputerDTO> validator = createComputerValidatorFactory.get(computer);
	if (validator.isValid()) {
	    computerDAO.create(createComputerDTOToComputer.apply(computer));
	} else {
	    throw new ValidatorException(validator.errors());
	}
    }

    @Override
    public void delete(long id) {
	computerDAO.deleteById(id);
    }

    @Override
    public boolean exist(long id) {
	return computerDAO.findById(id).isPresent();
    }

    @Override
    public List<ComputerDTO> findAll() {
	return mapAll(computerDAO.findAll());
    }

    @Override
    public List<ComputerDTO> findAll(long from, long to) {
	return mapAll(computerDAO.findAll(from, to));
    }

    /**
     * @throws {@link ComputerNotFound} si l'ordinateur est introuvable.
     */
    @Override
    public ComputerDTO findById(long id) {
	return computerDAO.findById(id).map(computerToComputerDTO::apply)
		.orElseThrow(() -> new ComputerNotFoundException(id));
    }

    private List<ComputerDTO> mapAll(List<Computer> companies) {
	return companies.stream().map(computerToComputerDTO::apply).collect(Collectors.toList());
    }

    /**
     * @throws {@link ValidatorException}
     */
    @Override
    public void update(UpdateComputerDTO computer) {
	Validator<UpdateComputerDTO> validator = updateComputerValidatorFactory.get(computer);
	if (validator.isValid()) {
	    computerDAO.update(updateComputerDTOToComputer.apply(computer));
	} else {
	    throw new ValidatorException(validator.errors());
	}
    }
}
