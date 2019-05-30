package com.excilys.cdb.cli.controller;

import com.excilys.cdb.cli.exception.ControllerException;
import com.excilys.cdb.cli.mapper.MapperUtils;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.shared.dto.CompanyDTO;
import com.excilys.cdb.shared.dto.ComputerDTO;
import com.excilys.cdb.shared.dto.CreateComputerDTO;
import com.excilys.cdb.shared.dto.CreateComputerDTO.CreateComputerDTOBuilder;
import com.excilys.cdb.shared.dto.UpdateComputerDTO;
import com.excilys.cdb.shared.mapper.CreateComputerDTOToComputerMapper;
import com.excilys.cdb.shared.mapper.Mapper;
import com.excilys.cdb.shared.mapper.UpdateComputerDTOToComputerMapper;
import com.excilys.cdb.shared.pagination.OrderBy;
import com.excilys.cdb.shared.pagination.Page;
import com.excilys.cdb.shared.pagination.Pageable;
import com.excilys.cdb.shared.validator.CreateComputerValidator;
import com.excilys.cdb.shared.validator.UpdateComputerValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.ValidationUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class Controller {
    private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);
    private final CompanyService companyService;
    private final Mapper<Company, CompanyDTO> companyToCompanyDTO;
    private final ComputerService computerService;
    private final Mapper<Computer, ComputerDTO> computerToComputerDTO;
    private final CreateComputerDTOToComputerMapper createComputerDTOToComputerMapper;
    private final CreateComputerValidator createComputerValidator;
    private final UpdateComputerDTOToComputerMapper updateComputerDTOToComputerMapper;
    private final UpdateComputerValidator updateComputerValidator;

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
            LOGGER.error("deleteComputer(" + id + ")", e);
            throw new ControllerException();
        }
    }

    public List<CompanyDTO> getCompanies(long offset, long limit) {
        try {
            final Page page = Page.builder().page(offset / limit + 1).size(limit).build();
            return companyService.findAll(page).stream().map(companyToCompanyDTO::map).collect(Collectors.toList());
        } catch (RuntimeException e) {
            LOGGER.error("getCompanies(" + offset + "," + limit + ")", e);
            throw new ControllerException();
        }
    }

    public Optional<ComputerDTO> getComputerById(long id) {
        try {
            return computerService.findById(id).map(computerToComputerDTO::map);
        } catch (RuntimeException e) {
            LOGGER.error("getComputerById(" + id + ")", e);
            throw new ControllerException();
        }
    }

    public List<ComputerDTO> getComputers(long offset, long limit) {
        try {
            final Page page = Page.builder().page(offset / limit + 1).size(limit).build();
            final OrderBy orderBy = OrderBy.builder().build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            return computerService.findAll(pageable).stream().map(computerToComputerDTO::map)
                    .collect(Collectors.toList());
        } catch (RuntimeException e) {
            LOGGER.error("getComputers(" + offset + "," + limit + ")", e);
            throw new ControllerException();
        }
    }

    public long numberOfCompanies() {
        try {
            return companyService.count();
        } catch (RuntimeException e) {
            LOGGER.error("numberOfCompanies()", e);
            throw new ControllerException();
        }
    }

    public long numberOfComputers() {
        try {
            return computerService.count();
        } catch (RuntimeException e) {
            LOGGER.error("numberOfComputers()", e);
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
            LOGGER.error("deleteCompany(" + id + ")", e);
            throw new ControllerException();
        }
    }

    public void createComputer(String name, String introduced, String discontinued, String manufacturerId)
            throws BindException {
        try {
            final CreateComputerDTOBuilder builder = CreateComputerDTO.builder().name(name);
            builder.introduced(MapperUtils.parseDate(introduced));
            builder.discontinued(MapperUtils.parseDate(discontinued));
            builder.manufacturerId(MapperUtils.parseId(manufacturerId));
            final CreateComputerDTO dto = builder.build();
            final BindException errors = new BindException(dto, "CreateComputerDTO");
            ValidationUtils.invokeValidator(createComputerValidator, dto, errors);
            if (errors.hasErrors()) {
                throw errors;
            }
            computerService.create(createComputerDTOToComputerMapper.map(dto));
        } catch (RuntimeException e) {
            LOGGER.error(
                    "createComputer(" + name + ", " + introduced + ", " + discontinued + ", " + manufacturerId + ")",
                    e);
            throw new ControllerException();
        }
    }

    public void updateComputer(String id, String name, String introduced, String discontinued, String manufacturerId)
            throws BindException {
        try {
            final UpdateComputerDTO.UpdateComputerDTOBuilder builder = UpdateComputerDTO.builder().name(name);
            builder.introduced(MapperUtils.parseDate(introduced));
            builder.discontinued(MapperUtils.parseDate(discontinued));
            builder.manufacturerId(MapperUtils.parseId(manufacturerId));
            builder.id(MapperUtils.parseId(id));
            final UpdateComputerDTO dto = builder.build();
            final BindException errors = new BindException(dto, "UpdateComputerDTO");
            ValidationUtils.invokeValidator(updateComputerValidator, dto, errors);
            if (errors.hasErrors()) {
                throw errors;
            }
            computerService.update(updateComputerDTOToComputerMapper.map(dto));
        } catch (RuntimeException e) {
            LOGGER.error("updateComputer(" + id + ", " + name + ", " + introduced + ", " + discontinued + ", "
                    + manufacturerId + ")", e);
            throw new ControllerException();
        }
    }

}
