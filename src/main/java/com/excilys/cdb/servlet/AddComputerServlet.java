package com.excilys.cdb.servlet;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.dto.CreateComputerDTO;
import com.excilys.cdb.exception.ValidationException;
import com.excilys.cdb.mapper.dto.CompanyToCompanyDTOMapper;
import com.excilys.cdb.mapper.dto.CreateComputerDTOToComputerMapper;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.validator.CreateComputerValidator;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
    private final CompanyService companyService = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext()).getBean(CompanyService.class);
    private final CreateComputerDTOToComputerMapper createComputerDTOToComputerMapper = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext()).getBean(CreateComputerDTOToComputerMapper.class);
    private final CreateComputerValidator createComputerValidator = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext()).getBean(CreateComputerValidator.class);


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        final List<CompanyDTO> companies = companyService.findAll().stream()
                .map(CompanyToCompanyDTOMapper.getInstance()::map).collect(Collectors.toList());
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

        final CreateComputerDTO createComputerDTO = CreateComputerDTO.builder().name(name).introduced(introduced).discontinued(discontinued).mannufacturerId(mannufacturerId).build();

        try {
            createComputerValidator.check(createComputerDTO);
            final Computer computer = createComputerDTOToComputerMapper.map(createComputerDTO);
            ComputerService.getInstance().create(computer);
            request.setAttribute(PARAMETER_SUCCESS, true);
        } catch (ValidationException e) {
            request.setAttribute(PARAMETER_SUCCESS, false);
            request.setAttribute(PARAMETER_COMPUTER, createComputerDTO);
            request.setAttribute(PARAMETER_ERRORS, Collections.singletonMap(e.getField(), e.getMessage()));
        }
        doGet(request, response);
    }
}
