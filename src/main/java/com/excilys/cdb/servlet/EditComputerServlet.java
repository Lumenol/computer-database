package com.excilys.cdb.servlet;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.dto.UpdateComputerDTO;
import com.excilys.cdb.exception.ValidationException;
import com.excilys.cdb.mapper.dto.CompanyToCompanyDTOMapper;
import com.excilys.cdb.mapper.dto.ComputerToUpdateComputerDTOMapper;
import com.excilys.cdb.mapper.dto.UpdateComputerDTOToComputerMapper;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.validator.UpdateComputerValidator;

public class EditComputerServlet extends HttpServlet {
    private static final String PARAMETER_ERRORS = "errors";
    private static final String PARAMETER_COMPUTER = "computer";
    private static final String PARAMETER_SUCCESS = "success";
    private static final long serialVersionUID = 1L;
    private static final String PARAMETER_COMPANIES = "companies";
    private static final String EDIT_COMPUTER_JSP = "/WEB-INF/views/editComputer.jsp";
    private static final String PARAMETER_ID = "id";
    private static final String PARAMETER_COMPUTER_NAME = "name";
    private static final String PARAMETER_INTRODUCED = "introduced";
    private static final String PARAMETER_DISCONTINUED = "discontinued";
    private static final String PARAMETER_MANNUFACTURER_ID = "mannufacturerId";
    private CompanyService companyService;
    private UpdateComputerDTOToComputerMapper updateComputerDTOToComputerMapper;
    private UpdateComputerValidator updateComputerValidator;
    private CompanyToCompanyDTOMapper companyToCompanyDTOMapper;
    private ComputerService computerService;
    private ComputerToUpdateComputerDTOMapper computerToUpdateComputerDTOMapper;

    @Override
    public void init() throws ServletException {
	super.init();
	final WebApplicationContext webApplicationContext = WebApplicationContextUtils
		.getRequiredWebApplicationContext(getServletContext());
	companyService = webApplicationContext.getBean(CompanyService.class);
	updateComputerDTOToComputerMapper = webApplicationContext.getBean(UpdateComputerDTOToComputerMapper.class);
	updateComputerValidator = webApplicationContext.getBean(UpdateComputerValidator.class);
	companyToCompanyDTOMapper = webApplicationContext.getBean(CompanyToCompanyDTOMapper.class);
	computerService = webApplicationContext.getBean(ComputerService.class);
	computerToUpdateComputerDTOMapper = webApplicationContext.getBean(ComputerToUpdateComputerDTOMapper.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {

	try {
	    final long id = Long.parseLong(getParameterId(request));
	    final Optional<UpdateComputerDTO> computer = computerService.findById(id)
		    .map(computerToUpdateComputerDTOMapper::map);

	    if (!computer.isPresent()) {
		response.sendError(404);
		return;
	    } else {
		setParameterComputer(request, computer.get());
	    }

	} catch (NumberFormatException e) {
	    response.sendError(404);
	    return;
	}

	setParameterCompanies(request);
	forwardToJsp(request, response);
    }

    private void forwardToJsp(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	getServletContext().getRequestDispatcher(EDIT_COMPUTER_JSP).forward(request, response);
    }

    private void setParameterCompanies(HttpServletRequest request) {
	final List<CompanyDTO> companies = companyService.findAll().stream().map(companyToCompanyDTOMapper::map)
		.collect(Collectors.toList());
	request.setAttribute(PARAMETER_COMPANIES, companies);
    }

    private void setParameterComputer(HttpServletRequest request, final UpdateComputerDTO computer) {
	request.setAttribute(PARAMETER_COMPUTER, computer);
    }

    private String getParameterId(HttpServletRequest request) {
	return request.getParameter(PARAMETER_ID);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	final String id = getParameterId(request);
	final String name = request.getParameter(PARAMETER_COMPUTER_NAME);
	final String introduced = request.getParameter(PARAMETER_INTRODUCED);
	final String discontinued = request.getParameter(PARAMETER_DISCONTINUED);
	final String mannufacturerId = request.getParameter(PARAMETER_MANNUFACTURER_ID);

	final UpdateComputerDTO updateComputerDTO = UpdateComputerDTO.builder().id(id).name(name).introduced(introduced)
		.discontinued(discontinued).mannufacturerId(mannufacturerId).build();

	try {
	    updateComputerValidator.check(updateComputerDTO);
	    final Computer computer = updateComputerDTOToComputerMapper.map(updateComputerDTO);
	    computerService.update(computer);
	    request.setAttribute(PARAMETER_SUCCESS, true);
	    doGet(request, response);
	} catch (ValidationException e) {
	    request.setAttribute(PARAMETER_SUCCESS, false);
	    request.setAttribute(PARAMETER_COMPUTER, updateComputerDTO);
	    request.setAttribute(PARAMETER_ERRORS, Collections.singletonMap(e.getField(), e.getMessage()));
	    setParameterCompanies(request);
	    forwardToJsp(request, response);
	}

    }
}
