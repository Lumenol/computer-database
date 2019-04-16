package com.metier.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.metier.ValidatorFactory;
import com.metier.dao.CompanyDAO;
import com.metier.dao.ComputerDAO;
import com.metier.dto.CompagnyDTO;
import com.metier.dto.ComputerDTO;
import com.metier.dto.CreateComputerDTO;
import com.metier.dto.UpdateComputerDTO;
import com.metier.entite.Company;
import com.metier.entite.Computer;
import com.metier.exception.CompagnyNotFoundException;
import com.metier.exception.ComputerNotFoundException;
import com.metier.validator.Validator;

public class ComputerServiceImpl implements ComputerService {

    private final ComputerDAO computerDAO;
    private final CompanyDAO compagnyDAO;
    private final ValidatorFactory<CreateComputerDTO> createComputerValidatorFactory;
    private final ValidatorFactory<UpdateComputerDTO> updateComputerValidatorFactory;

    private static ComputerDTO toComputerDTO(Computer computer) {
	ComputerDTO computerDTO = new ComputerDTO();
	computerDTO.setId(computer.getId());
	computerDTO.setName(computer.getName());
	computerDTO.setIntroduced(computer.getIntroduced());
	computerDTO.setDiscontinued(computer.getDiscontinued().orElse(null));

	if (Objects.nonNull(computer.getManufacturer())) {
	    CompagnyDTO mannufacturer = new CompagnyDTO();
	    mannufacturer.setName(computer.getManufacturer().getName());
	    computerDTO.setMannufacturer(mannufacturer);
	}

	return computerDTO;
    }

    private Computer from(UpdateComputerDTO dto) {
	Optional<Company> mannufacturer = compagnyDAO.findById(dto.getMannufacturerId());
	if (mannufacturer.isPresent()) {
	    return Computer.builder().id(dto.getId()).name(dto.getName()).introduced(dto.getIntroduced())
		    .discontinued(dto.getDiscontinued()).manufacturer(mannufacturer.get()).build();
	} else {
	    throw new CompagnyNotFoundException(dto.getMannufacturerId());
	}
    }

    private Computer from(CreateComputerDTO dto) {
	Optional<Company> mannufacturer = compagnyDAO.findById(dto.getMannufacturerId());
	if (mannufacturer.isPresent()) {
	    return Computer.builder().name(dto.getName()).introduced(dto.getIntroduced())
		    .discontinued(dto.getDiscontinued()).manufacturer(mannufacturer.get()).build();
	} else {
	    throw new CompagnyNotFoundException(dto.getMannufacturerId());
	}
    }

    public ComputerServiceImpl(ComputerDAO computerDAO, CompanyDAO compagnyDAO,
	    ValidatorFactory<CreateComputerDTO> createComputerValidatorFactory,
	    ValidatorFactory<UpdateComputerDTO> updateComputerValidatorFactory) {
	super();
	this.computerDAO = computerDAO;
	this.compagnyDAO = compagnyDAO;
	this.createComputerValidatorFactory = createComputerValidatorFactory;
	this.updateComputerValidatorFactory = updateComputerValidatorFactory;
    }

    @Override
    public List<ComputerDTO> findAll() {
	return computerDAO.findAll().stream().map(ComputerServiceImpl::toComputerDTO).collect(Collectors.toList());
    }

    /**
     * @throws {@link ComputerNotFound} si l'ordinateur est introuvable.
     */
    @Override
    public ComputerDTO findById(long id) {
	return computerDAO.findById(id).map(ComputerServiceImpl::toComputerDTO)
		.orElseThrow(() -> new ComputerNotFoundException(id));
    }

    @Override
    public void update(UpdateComputerDTO computer) {
	Validator<UpdateComputerDTO> validator = updateComputerValidatorFactory.get(computer);
	if (validator.isValid()) {
	    computerDAO.update(from(computer));
	}
    }

    @Override
    public void create(CreateComputerDTO computer) {
	Validator<CreateComputerDTO> validator = createComputerValidatorFactory.get(computer);
	if (validator.isValid()) {
	    computerDAO.create(from(computer));
	}
    }

    @Override
    public void delete(long id) {
	computerDAO.deleteById(id);
    }

}
