package com.metier.service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.metier.ValidatorFactory;
import com.metier.dao.ComputerDAO;
import com.metier.dto.ComputerDTO;
import com.metier.dto.CreateComputerDTO;
import com.metier.dto.UpdateComputerDTO;
import com.metier.entite.Computer;
import com.metier.exception.ComputerNotFoundException;
import com.metier.validator.Validator;

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

}
