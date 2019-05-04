package com.excilys.cdb.controller;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.dto.CreateComputerDTO;
import com.excilys.cdb.dto.UpdateComputerDTO;
import com.excilys.cdb.exception.ControllerException;
import com.excilys.cdb.exception.ValidationException;
import com.excilys.cdb.mapper.dto.*;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.validator.CreateComputerValidator;
import com.excilys.cdb.validator.UpdateComputerValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class Controller {
    private static Controller instance;
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

    public static synchronized Controller getInstance() {
        if (Objects.isNull(instance)) {
            instance = new Controller();
        }
        return instance;
    }

    public void createComputer(CreateComputerDTO dto) {
        try {
            createComputerValidator.check(dto);
            computerService.create(createComputerDTOToComputerMapper.map(dto));
        } catch (ValidationException e) {
            logger.warn("createComputer(" + dto + ")", e);
            throw e;
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
            updateComputerValidator.check(dto);
            computerService.update(updateComputerDTOToComputerMapper.map(dto));
        } catch (ValidationException e) {
            logger.warn("updateComputer(" + dto + ")", e);
            throw e;
        } catch (RuntimeException e) {
            logger.warn("updateComputer(" + dto + ")", e);
            throw new ControllerException();
        }
    }
}
