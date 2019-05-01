package com.excilys.cdb.servlet;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.dto.CreateComputerDTO;
import com.excilys.cdb.dto.CreateComputerDTOUi;
import com.excilys.cdb.mapper.dto.CompanyToCompanyDTOMapper;
import com.excilys.cdb.mapper.dto.CreateComputerDTOToComputerMapper;
import com.excilys.cdb.mapper.dto.CreateComputerDTOUiToCreateComputerDTOMapper;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.validator.CreateComputerValidator;
import com.excilys.cdb.validator.CreateComputerWebUiValidator;
import com.excilys.cdb.validator.Result;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class AddComputerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String PARAMETER_COMPANIES = "companies";
    private static final String ADD_COMPUTER_JSP = "/WEB-INF/views/addComputer.jsp";
    private static final String PARAMETER_COMPUTER_NAME = "name";
    private static final String PARAMETER_INTRODUCED = "introduced";
    private static final String PARAMETER_DISCONTINUED = "discontinued";
    private static final String PARAMETER_MANNUFACTURER_ID = "mannufacturerId";
    private final CompanyService companyService = CompanyService.getInstance();
    private final CreateComputerWebUiValidator createComputerWebUiValidator = CreateComputerWebUiValidator.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        final List<CompanyDTO> companies = companyService.findAll().stream().map(CompanyToCompanyDTOMapper.getInstance()::map).collect(Collectors.toList());
        request.setAttribute(PARAMETER_COMPANIES, companies);
        getServletContext().getRequestDispatcher(ADD_COMPUTER_JSP).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final String name = request.getParameter(PARAMETER_COMPUTER_NAME);
        final String introduced = request.getParameter(PARAMETER_INTRODUCED);
        final String discontinued = request.getParameter(PARAMETER_DISCONTINUED);
        final String mannufacturerId = request.getParameter(PARAMETER_MANNUFACTURER_ID);

        final CreateComputerDTOUi createComputerDTOUi = new CreateComputerDTOUi();
        createComputerDTOUi.setName(name);
        createComputerDTOUi.setIntroduced(introduced);
        createComputerDTOUi.setDiscontinued(discontinued);
        createComputerDTOUi.setMannufacturerId(mannufacturerId);

        Result result = createComputerWebUiValidator.check(createComputerDTOUi);
        if (result.isValid()) {
            final CreateComputerDTO createComputerDTO = CreateComputerDTOUiToCreateComputerDTOMapper.getInstance().map(createComputerDTOUi);
            result = CreateComputerValidator.getInstance().check(createComputerDTO);
            if (result.isValid()) {
                final Computer computer = CreateComputerDTOToComputerMapper.getInstance().map(createComputerDTO);
                ComputerService.getInstance().create(computer);
                request.setAttribute("success", true);
            }
        }
        if (!result.isValid()) {
            request.setAttribute("success", false);
            request.setAttribute("computer", createComputerDTOUi);
            request.setAttribute("errors", result.getErrors());
        }
        doGet(request, response);
    }
}
