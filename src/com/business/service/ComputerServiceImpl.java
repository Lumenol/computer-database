package com.business.service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.business.ValidatorFactory;
import com.business.dao.ComputerDAO;
import com.business.dto.ComputerDTO;
import com.business.dto.CreateComputerDTO;
import com.business.dto.UpdateComputerDTO;
import com.business.entite.Computer;
import com.business.exception.ComputerNotFoundException;
import com.business.validator.Validator;

public class ComputerServiceImpl implements ComputerService {

    private final ComputerDAO computerDAO;
    private final ValidatorFactory<CreateComputerDTO> createComputerValidatorFactory;
    private final ValidatorFactory<UpdateComputerDTO> updateComputerValidatorFactory;
    private final Function<Computer, ComputerDTO> computerToComputerDTO;
    private final Function<UpdateComputerDTO, Computer> updateComputerDTOToComputer;
    private final Function<CreateComputerDTO, Computer> createComputerDTOToComputer;

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

    @Override
    public void create(CreateComputerDTO computer) {
	Validator<CreateComputerDTO> validator = createComputerValidatorFactory.get(computer);
	if (validator.isValid()) {
	    computerDAO.create(createComputerDTOToComputer.apply(computer));
	}
    }

    @Override
    public void delete(long id) {
	computerDAO.deleteById(id);
    }

    @Override
    public List<ComputerDTO> findAll() {
	return computerDAO.findAll().stream().map(computerToComputerDTO::apply).collect(Collectors.toList());
    }

    /**
     * @throws {@link ComputerNotFound} si l'ordinateur est introuvable.
     */
    @Override
    public ComputerDTO findById(long id) {
	return computerDAO.findById(id).map(computerToComputerDTO::apply)
		.orElseThrow(() -> new ComputerNotFoundException(id));
    }

    @Override
    public void update(UpdateComputerDTO computer) {
	Validator<UpdateComputerDTO> validator = updateComputerValidatorFactory.get(computer);
	if (validator.isValid()) {
	    computerDAO.update(updateComputerDTOToComputer.apply(computer));
	}
    }

}
