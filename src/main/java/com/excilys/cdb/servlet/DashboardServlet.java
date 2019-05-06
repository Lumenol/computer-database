package com.excilys.cdb.servlet;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.exception.BadArgumentRequestException;
import com.excilys.cdb.mapper.dto.ComputerToComputerDTOMapper;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.servlet.pagination.Pagination;
import com.excilys.cdb.servlet.pagination.PaginationParameters;

public class DashboardServlet extends HttpServlet {
    private final Pagination pagination = Pagination.DEFAULT_PAGINATION;

    private static final String DASHBOARD_JSP = "/WEB-INF/views/dashboard.jsp";

    private static final String PARAMETER_COMPUTERS = "computers";
    private static final String PARAMETER_NUMBER_OF_COMPUTERS = "numberOfComputers";

    private static final long serialVersionUID = 1L;
    private static final String DASHBOARD = "dashboard";
    private final ComputerService computerService = ComputerService.getInstance();

    private List<Long> getRemoveComputersId(HttpServletRequest request) {
	try {
	    final String[] selections = request.getParameterValues("selection");
	    if (Objects.isNull(selections)) {
		return Collections.emptyList();
	    } else {
		return Arrays.stream(selections).map(Long::valueOf).collect(Collectors.toList());
	    }
	} catch (NumberFormatException e) {
	    throw new BadArgumentRequestException("Les identifiants des ordinateurs à supprimer sont incorects", e);
	}
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	final List<Long> removeComputersId = getRemoveComputersId(request);
	removeComputersId.stream().forEach(computerService::delete);
	final long page = pagination.getPageIndex(request);
	final long size = pagination.getPageSize(request);
	redirectToPageNumber(response, page, size);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	final long pageIndex = pagination.getPageIndex(request);
	final long pageSize = pagination.getPageSize(request);

	final long numberOfComputers = computerService.count();
	final long pageOutOfRange = pagination.redirectIfPageOutOfRange(request, numberOfComputers);
	if (pageOutOfRange > 0) {
	    redirectToPageNumber(response, pageOutOfRange, pageSize);
	    return;
	}

	final long offset = (pageIndex - 1) * pageSize;
	final List<ComputerDTO> computers = computerService.findAll(offset, pageSize).stream()
		.map(ComputerToComputerDTOMapper.getInstance()::map).collect(Collectors.toList());

	setNumberOfComputers(request, numberOfComputers);
	setComputers(request, computers);

	pagination.setPaggingParameters(request, pageIndex, numberOfComputers, pageSize);

	getServletContext().getRequestDispatcher(DASHBOARD_JSP).forward(request, response);
    }

    private void redirectToPageNumber(HttpServletResponse response, long pageNumber, long pageSize) throws IOException {
	final PaginationParameters parameters = pagination.getParameters();
	response.sendRedirect(DASHBOARD + "?" + parameters.getPage() + "=" + pageNumber + "&" + parameters.getSize()
		+ "=" + pageSize);
    }

    private void setComputers(HttpServletRequest request, List<ComputerDTO> computers) {
	request.setAttribute(PARAMETER_COMPUTERS, computers);
    }

    private void setNumberOfComputers(HttpServletRequest request, long numberOfComputers) {
	request.setAttribute(PARAMETER_NUMBER_OF_COMPUTERS, numberOfComputers);
    }

}
