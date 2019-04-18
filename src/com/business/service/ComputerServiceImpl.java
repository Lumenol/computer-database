package com.business.service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.business.ValidatorFactory;
import com.business.dao.ComputerDAO;
import com.business.dto.CompanyDTO;
import com.business.dto.ComputerDTO;
import com.business.dto.CreateComputerDTO;
import com.business.dto.UpdateComputerDTO;
import com.business.entite.Company;
import com.business.entite.Computer;
import com.business.exception.ComputerNotFoundException;
import com.business.exception.ValidatorException;
import com.business.validator.Validator;

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

    /**
     * @throws {@link ComputerNotFound} si l'ordinateur est introuvable.
     */
    @Override
    public ComputerDTO findById(long id) {
	return computerDAO.findById(id).map(computerToComputerDTO::apply)
		.orElseThrow(() -> new ComputerNotFoundException(id));
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

    @Override
    public List<ComputerDTO> findAll(long from, long to) {
	return mapAll(computerDAO.findAll(from,to));
    }

    private List<ComputerDTO> mapAll(List<Computer> companies) {
	return companies.stream().map(computerToComputerDTO::apply).collect(Collectors.toList());
    }
}
