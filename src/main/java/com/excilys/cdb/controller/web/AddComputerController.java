package com.excilys.cdb.controller.web;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.dto.CreateComputerDTO;
import com.excilys.cdb.dto.CreateComputerDTO.CreateComputerDTOBuilder;
import com.excilys.cdb.exception.ValidationException;
import com.excilys.cdb.mapper.MapperUtils;
import com.excilys.cdb.mapper.dto.CompanyToCompanyDTOMapper;
import com.excilys.cdb.mapper.dto.CreateComputerDTOToComputerMapper;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.validator.CreateComputerValidator;

@Controller
@RequestMapping("/computers/add")
public class AddComputerController {
    private static final String PARAMETER_ERRORS = "errors";
    private static final String PARAMETER_SUCCESS = "success";
    private static final String PARAMETER_COMPANIES = "companies";
    private static final String ADD_COMPUTER_JSP = "addComputer";
    private static final String PARAMETER_COMPUTER_NAME = "name";
    private static final String PARAMETER_INTRODUCED = "introduced";
    private static final String PARAMETER_DISCONTINUED = "discontinued";
    private static final String PARAMETER_MANNUFACTURER_ID = "mannufacturerId";
    private final CompanyService companyService;
    private final CreateComputerDTOToComputerMapper createComputerDTOToComputerMapper;
    private final CreateComputerValidator createComputerValidator;
    private final CompanyToCompanyDTOMapper companyToCompanyDTOMapper;
    private final ComputerService computerService;

    public AddComputerController(CompanyService companyService,
	    CreateComputerDTOToComputerMapper createComputerDTOToComputerMapper,
	    CreateComputerValidator createComputerValidator, CompanyToCompanyDTOMapper companyToCompanyDTOMapper,
	    ComputerService computerService) {
	super();
	this.companyService = companyService;
	this.createComputerDTOToComputerMapper = createComputerDTOToComputerMapper;
	this.createComputerValidator = createComputerValidator;
	this.companyToCompanyDTOMapper = companyToCompanyDTOMapper;
	this.computerService = computerService;
    }

    @GetMapping
    public ModelAndView doGet() {
	final List<CompanyDTO> companies = companyService.findAll().stream().map(companyToCompanyDTOMapper::map)
		.collect(Collectors.toList());
	final ModelAndView modelAndView = new ModelAndView(ADD_COMPUTER_JSP);
	modelAndView.addObject(PARAMETER_COMPANIES, companies);
	return modelAndView;
    }

    @PostMapping
    public ModelAndView doPost(@RequestParam String name, @RequestParam String introduced,
	    @RequestParam String discontinued, @RequestParam String mannufacturerId) {
	final ModelAndView modelAndView = doGet();
	try {
	    final CreateComputerDTOBuilder builder = CreateComputerDTO.builder().name(name);
	    builder.introduced(MapperUtils.parseDate("introduced", introduced));
	    builder.discontinued(MapperUtils.parseDate("discontinued", discontinued));
	    builder.mannufacturerId(MapperUtils.parseId("mannufacturerId", mannufacturerId));
	    final CreateComputerDTO dto = builder.build();
	    createComputerValidator.check(dto);
	    final Computer computer = createComputerDTOToComputerMapper.map(dto);
	    computerService.create(computer);
	    modelAndView.addObject(PARAMETER_SUCCESS, true);
	} catch (ValidationException e) {
	    modelAndView.addObject(PARAMETER_SUCCESS, false);
	    modelAndView.addObject(PARAMETER_COMPUTER_NAME, name);
	    modelAndView.addObject(PARAMETER_INTRODUCED, introduced);
	    modelAndView.addObject(PARAMETER_DISCONTINUED, discontinued);
	    modelAndView.addObject(PARAMETER_MANNUFACTURER_ID, mannufacturerId);
	    modelAndView.addObject(PARAMETER_ERRORS, Collections.singletonMap(e.getField(), e.getMessage()));
	}
	return modelAndView;
    }
}
