package com.excilys.cdb.controller;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.dto.CreateComputerDTO;
import com.excilys.cdb.dto.UpdateComputerDTO;
import com.excilys.cdb.exception.ControllerException;
import com.excilys.cdb.exception.ValidatorException;
import com.excilys.cdb.mapper.dto.CompanyToCompanyDTOMapper;
import com.excilys.cdb.mapper.dto.ComputerToComputerDTOMapper;
import com.excilys.cdb.mapper.dto.CreateComputerDTOToComputerMapper;
import com.excilys.cdb.mapper.dto.Mapper;
import com.excilys.cdb.mapper.dto.UpdateComputerDTOToComputerMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.validator.CreateComputerValidator;
import com.excilys.cdb.validator.UpdateComputerValidator;
import com.excilys.cdb.validator.Validator;

public class Controller {
    private static Controller instance;
    public static Controller getInstance() {
	if (Objects.isNull(instance)) {
	    instance = new Controller();
	}
	return instance;
    }
    private final CompanyService companyService = CompanyService.getInstance();
    private final Mapper<Company, CompanyDTO> companyToCompanyDTO = CompanyToCompanyDTOMapper.getInstance();
    private final ComputerService computerService = ComputerService.getInstance();
    private final Mapper<Computer, ComputerDTO> computerToComputerDTO = ComputerToComputerDTOMapper.getInstance();
    private final CreateComputerDTOToComputerMapper createComputerDTOToComputerMapper = CreateComputerDTOToComputerMapper
	    .getInstance();
    private final CreateComputerValidator createComputerValidator = CreateComputerValidator.getInstance();
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final UpdateComputerDTOToComputerMapper updateComputerDTOToComputerMapper = UpdateComputerDTOToComputerMapper
	    .getInstance();

    private final UpdateComputerValidator updateComputerValidator = UpdateComputerValidator.getInstance();

    private Controller() {
    }

    public void createComputer(CreateComputerDTO dto) {
	try {
	    final Validator.Result result = createComputerValidator.check(dto);
	    if (result.isValid()) {
		computerService.create(createComputerDTOToComputerMapper.map(dto));
	    } else {
		throw new ValidatorException(result);
	    }
	} catch (ValidatorException e) {
	    logger.warn("createComputer(" + dto + ")", e);
	    throw new ControllerException(e.getMessage());
	} catch (RuntimeException e) {
	    logger.warn("createComputer(" + dto + ")", e);
	    throw new ControllerException();
	}
    }

    public void deleteComputer(long id) {
	try {
	    computerService.delete(id);
	} catch (RuntimeException e) {
	    logger.warn("deleteComputer(" + id + ")", e);
	    throw new ControllerException();
	}
    }

    public List<CompanyDTO> getCompanies(long offset, long limit) {
	try {
	    return companyService.findAll(offset, limit).stream().map(companyToCompanyDTO::map)
		    .collect(Collectors.toList());
	} catch (RuntimeException e) {
	    logger.warn("getCompanies(" + offset + "," + limit + ")", e);
	    throw new ControllerException();
	}
    }

    public Optional<ComputerDTO> getComputerById(long id) {
	try {
	    return computerService.findById(id).map(computerToComputerDTO::map);
	} catch (RuntimeException e) {
	    logger.warn("getComputerById(" + id + ")", e);
	    throw new ControllerException();
	}
    }

    public List<ComputerDTO> getComputers(long offset, long limit) {
	try {
	    return computerService.findAll(offset, limit).stream().map(computerToComputerDTO::map)
		    .collect(Collectors.toList());
	} catch (RuntimeException e) {
	    logger.warn("getComputers(" + offset + "," + limit + ")", e);
	    throw new ControllerException();
	}
    }

    public long numberOfCompanies() {
	try {
	    return companyService.count();
	} catch (RuntimeException e) {
	    logger.warn("numberOfCompanies()", e);
	    throw new ControllerException();
	}
    }

    public long numberOfComputers() {
	try {
	    return computerService.count();
	} catch (RuntimeException e) {
	    logger.warn("numberOfComputers()", e);
	    throw new ControllerException();
	}
    }

    public void updateComputer(UpdateComputerDTO dto) {
	try {
	    final Validator.Result result = updateComputerValidator.check(dto);
	    if (result.isValid()) {
		computerService.update(updateComputerDTOToComputerMapper.map(dto));
	    } else {
		throw new ValidatorException(result);
	    }
	} catch (ValidatorException e) {
	    logger.warn("updateComputer(" + dto + ")", e);
	    throw new ControllerException(e.getMessage());
	} catch (RuntimeException e) {
	    logger.warn("updateComputer(" + dto + ")", e);
	    throw new ControllerException();
	}
    }
}
