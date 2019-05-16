package com.excilys.cdb.servlet;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

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

public class AddComputerServlet extends HttpServlet {
    private static final String PARAMETER_ERRORS = "errors";
    private static final String PARAMETER_COMPUTER = "computer";
    private static final String PARAMETER_SUCCESS = "success";
    private static final long serialVersionUID = 1L;
    private static final String PARAMETER_COMPANIES = "companies";
    private static final String ADD_COMPUTER_JSP = "/WEB-INF/views/addComputer.jsp";
    private static final String PARAMETER_COMPUTER_NAME = "name";
    private static final String PARAMETER_INTRODUCED = "introduced";
    private static final String PARAMETER_DISCONTINUED = "discontinued";
    private static final String PARAMETER_MANNUFACTURER_ID = "mannufacturerId";
    private CompanyService companyService;
    private CreateComputerDTOToComputerMapper createComputerDTOToComputerMapper;
    private CreateComputerValidator createComputerValidator;
    private CompanyToCompanyDTOMapper companyToCompanyDTOMapper;
    private ComputerService computerService;

    @Override
    public void init() throws ServletException {
	super.init();
	final WebApplicationContext webApplicationContext = WebApplicationContextUtils
		.getRequiredWebApplicationContext(getServletContext());
	companyService = webApplicationContext.getBean(CompanyService.class);
	createComputerDTOToComputerMapper = webApplicationContext.getBean(CreateComputerDTOToComputerMapper.class);
	createComputerValidator = webApplicationContext.getBean(CreateComputerValidator.class);
	companyToCompanyDTOMapper = webApplicationContext.getBean(CompanyToCompanyDTOMapper.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	final List<CompanyDTO> companies = companyService.findAll().stream().map(companyToCompanyDTOMapper::map)
		.collect(Collectors.toList());
	request.setAttribute(PARAMETER_COMPANIES, companies);
	getServletContext().getRequestDispatcher(ADD_COMPUTER_JSP).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	final String name = request.getParameter(PARAMETER_COMPUTER_NAME);
	final String introduced = request.getParameter(PARAMETER_INTRODUCED);
	final String discontinued = request.getParameter(PARAMETER_DISCONTINUED);
	final String mannufacturerId = request.getParameter(PARAMETER_MANNUFACTURER_ID);

	try {
	    final CreateComputerDTOBuilder builder = CreateComputerDTO.builder().name(name);
	    builder.introduced(MapperUtils.parseDate("introduced", introduced));
	    builder.discontinued(MapperUtils.parseDate("discontinued", discontinued));
	    builder.mannufacturerId(MapperUtils.parseId("mannufacturerId", mannufacturerId));
	    final CreateComputerDTO dto = builder.build();
	    createComputerValidator.check(dto);
	    final Computer computer = createComputerDTOToComputerMapper.map(dto);
	    computerService.create(computer);
	    request.setAttribute(PARAMETER_SUCCESS, true);
	} catch (ValidationException e) {
	    request.setAttribute(PARAMETER_SUCCESS, false);
	    request.setAttribute(PARAMETER_COMPUTER_NAME, name);
	    request.setAttribute(PARAMETER_INTRODUCED, introduced);
	    request.setAttribute(PARAMETER_DISCONTINUED, discontinued);
	    request.setAttribute(PARAMETER_MANNUFACTURER_ID, mannufacturerId);
	    request.setAttribute(PARAMETER_ERRORS, Collections.singletonMap(e.getField(), e.getMessage()));
	}
	doGet(request, response);
    }
}
