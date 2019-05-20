package com.excilys.cdb.controller.cli;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.ValidationUtils;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.dto.CreateComputerDTO;
import com.excilys.cdb.dto.CreateComputerDTO.CreateComputerDTOBuilder;
import com.excilys.cdb.dto.UpdateComputerDTO;
import com.excilys.cdb.exception.ControllerException;
import com.excilys.cdb.mapper.MapperUtils;
import com.excilys.cdb.mapper.dto.CreateComputerDTOToComputerMapper;
import com.excilys.cdb.mapper.dto.Mapper;
import com.excilys.cdb.mapper.dto.UpdateComputerDTOToComputerMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.page.OrderBy;
import com.excilys.cdb.persistence.page.Page;
import com.excilys.cdb.persistence.page.Pageable;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.validator.CreateComputerValidator;
import com.excilys.cdb.validator.UpdateComputerValidator;

@Component
public class Controller {
    private final CompanyService companyService;
    private final Mapper<Company, CompanyDTO> companyToCompanyDTO;
    private final ComputerService computerService;
    private final Mapper<Computer, ComputerDTO> computerToComputerDTO;
    private final CreateComputerDTOToComputerMapper createComputerDTOToComputerMapper;
    private final CreateComputerValidator createComputerValidator;
    private final UpdateComputerDTOToComputerMapper updateComputerDTOToComputerMapper;
    private final UpdateComputerValidator updateComputerValidator;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public Controller(CompanyService companyService, Mapper<Company, CompanyDTO> companyToCompanyDTO,
	    ComputerService computerService, Mapper<Computer, ComputerDTO> computerToComputerDTO,
	    CreateComputerDTOToComputerMapper createComputerDTOToComputerMapper,
	    CreateComputerValidator createComputerValidator,
	    UpdateComputerDTOToComputerMapper updateComputerDTOToComputerMapper,
	    UpdateComputerValidator updateComputerValidator) {
	this.companyService = companyService;
	this.companyToCompanyDTO = companyToCompanyDTO;
	this.computerService = computerService;
	this.computerToComputerDTO = computerToComputerDTO;
	this.createComputerDTOToComputerMapper = createComputerDTOToComputerMapper;
	this.createComputerValidator = createComputerValidator;
	this.updateComputerDTOToComputerMapper = updateComputerDTOToComputerMapper;
	this.updateComputerValidator = updateComputerValidator;
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
	    final Page page = Page.builder().page(offset / limit + 1).limit(limit).build();
	    return companyService.findAll(page).stream().map(companyToCompanyDTO::map).collect(Collectors.toList());
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
	    final Page page = Page.builder().page(offset / limit + 1).limit(limit).build();
	    final OrderBy orderBy = OrderBy.builder().build();
	    final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
	    return computerService.findAll(pageable).stream().map(computerToComputerDTO::map)
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

    public boolean companyExist(long id) {
	return companyService.exist(id);
    }

    public void deleteCompany(long id) {
	try {
	    companyService.delete(id);
	} catch (RuntimeException e) {
	    logger.warn("deleteCompany(" + id + ")", e);
	    throw new ControllerException();
	}
    }

    public void createComputer(String name, String introduced, String discontinued, String mannufacturerId)
	    throws BindException {
	try {
	    final CreateComputerDTOBuilder builder = CreateComputerDTO.builder().name(name);
	    builder.introduced(MapperUtils.parseDate(introduced));
	    builder.discontinued(MapperUtils.parseDate(discontinued));
	    builder.mannufacturerId(MapperUtils.parseId(mannufacturerId));
	    final CreateComputerDTO dto = builder.build();
	    final BindException errors = new BindException(dto, "CreateComputerDTO");
	    ValidationUtils.invokeValidator(createComputerValidator, dto, errors);
	    if (errors.hasErrors()) {
		throw errors;
	    }
	    computerService.create(createComputerDTOToComputerMapper.map(dto));
	} catch (RuntimeException e) {
	    logger.warn(
		    "createComputer(" + name + ", " + introduced + ", " + discontinued + ", " + mannufacturerId + ")",
		    e);
	    throw new ControllerException();
	}
    }

    public void updateComputer(String id, String name, String introduced, String discontinued, String mannufacturerId)
	    throws BindException {
	try {
	    final UpdateComputerDTO.UpdateComputerDTOBuilder builder = UpdateComputerDTO.builder().name(name);
	    builder.introduced(MapperUtils.parseDate(introduced));
	    builder.discontinued(MapperUtils.parseDate(discontinued));
	    builder.mannufacturerId(MapperUtils.parseId(mannufacturerId));
	    builder.id(MapperUtils.parseId(id));
	    final UpdateComputerDTO dto = builder.build();
	    final BindException errors = new BindException(dto, "UpdateComputerDTO");
	    ValidationUtils.invokeValidator(updateComputerValidator, dto, errors);
	    if (errors.hasErrors()) {
		throw errors;
	    }
	    computerService.update(updateComputerDTOToComputerMapper.map(dto));
	} catch (RuntimeException e) {
	    logger.warn("updateComputer(" + id + ", " + name + ", " + introduced + ", " + discontinued + ", "
		    + mannufacturerId + ")", e);
	    throw new ControllerException();
	}
    }

}
