package com.excilys.cdb.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.exception.BadArgumentRequestException;
import com.excilys.cdb.mapper.dto.ComputerToComputerDTOMapper;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.page.OrderBy;
import com.excilys.cdb.persistence.page.Page;
import com.excilys.cdb.persistence.page.Pageable;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.servlet.pagination.Pagination;
import com.excilys.cdb.servlet.pagination.PaginationParameters;
import com.excilys.cdb.servlet.sorting.Sorting;
import com.excilys.cdb.servlet.sorting.SortingParameters;

public class DashboardServlet extends HttpServlet {

    private static final String PARAMETER_SEARCH = "search";
    private static final String DASHBOARD_JSP = "/WEB-INF/views/dashboard.jsp";
    private static final String PARAMETER_COMPUTERS = "computers";
    private static final String PARAMETER_NUMBER_OF_COMPUTERS = "numberOfComputers";
    private static final long serialVersionUID = 1L;
    private static final String DASHBOARD = "dashboard";
    private final Pagination pagination = Pagination.DEFAULT_PAGINATION;
    private final Sorting sorting = Sorting.DEFAULT_SORTING;
    private ComputerService computerService;
    private ComputerToComputerDTOMapper computerToComputerDTOMapper;

    @Override
    public void init() throws ServletException {
	super.init();
	final WebApplicationContext webApplicationContext = WebApplicationContextUtils
		.getRequiredWebApplicationContext(getServletContext());
	computerToComputerDTOMapper = webApplicationContext.getBean(ComputerToComputerDTOMapper.class);
	computerService = webApplicationContext.getBean(ComputerService.class);
    }

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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
	final List<Long> removeComputersId = getRemoveComputersId(request);
	removeComputersId.stream().forEach(computerService::delete);
	final OrderBy orderBy = sorting.getOrderBy(request);
	final Page page = pagination.getPage(request);
	final Pageable pageable = Pageable.builder().orderBy(orderBy).page(page).build();
	final String search = getParameterSearch(request);
	redirectToPageNumber(response, pageable, search);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	final String search = getParameterSearch(request);
	final OrderBy orderBy = sorting.getOrderBy(request);

	final long numberOfComputers = getComputerCount(search);
	final Page page = pagination.getPage(request);
	final long pageOutOfRange = pagination.redirectIfPageOutOfRange(request, numberOfComputers);
	if (pageOutOfRange > 0) {
	    final Page pageRedirection = Page.builder().page(pageOutOfRange).limit(page.getLimit()).build();
	    final Pageable pageable = Pageable.builder().page(pageRedirection).orderBy(orderBy).build();
	    redirectToPageNumber(response, pageable, search);
	    return;
	}

	final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
	final List<ComputerDTO> computers = getComputers(pageable, search);

	setNumberOfComputers(request, numberOfComputers);
	setComputers(request, computers);
	setSearch(request, search);
	pagination.setPageParameters(request, page, numberOfComputers);

	sorting.setOrderBy(request, orderBy);

	getServletContext().getRequestDispatcher(DASHBOARD_JSP).forward(request, response);
    }

    private List<ComputerDTO> getComputers(Pageable pageable, String search) {
	final List<Computer> computers;
	if (Objects.isNull(search)) {
	    computers = computerService.findAll(pageable);
	} else {
	    computers = computerService.search(pageable, search);
	}

	return computers.stream().map(computerToComputerDTOMapper::map).collect(Collectors.toList());
    }

    private long getComputerCount(String search) {
	if (Objects.isNull(search)) {
	    return computerService.count();
	} else {
	    return computerService.countSearch(search);
	}

    }

    private String getParameterSearch(HttpServletRequest request) {
	final String search = request.getParameter(PARAMETER_SEARCH);
	if (Objects.isNull(search) || search.isEmpty()) {
	    return null;
	}
	return search;
    }

    private void setSearch(HttpServletRequest request, String search) {
	request.setAttribute(PARAMETER_SEARCH, search);
    }

    private void redirectToPageNumber(HttpServletResponse response, Pageable pageable, String search)
	    throws IOException {
	final PaginationParameters paggingParameters = pagination.getParameters();
	final SortingParameters sortingParameters = sorting.getParameters();
	final StringBuilder stringBuilder = new StringBuilder(DASHBOARD).append("?");

	final Page page = pageable.getPage();
	stringBuilder.append(paggingParameters.getPage()).append("=").append(page.getPage());
	stringBuilder.append("&");
	stringBuilder.append(paggingParameters.getSize()).append("=").append(page.getLimit());

	if (Objects.nonNull(search)) {
	    stringBuilder.append("&");
	    stringBuilder.append(encode(PARAMETER_SEARCH)).append("=").append(encode(search));
	}

	final OrderBy orderBy = pageable.getOrderBy();
	if (Objects.nonNull(orderBy.getField())) {
	    stringBuilder.append("&");
	    stringBuilder.append(encode(sortingParameters.getOrderBy())).append("=")
		    .append(encode(orderBy.getField().getIdentifier()));
	}

	if (Objects.nonNull(orderBy.getMeaning())) {
	    stringBuilder.append("&");
	    stringBuilder.append(encode(sortingParameters.getMeaning())).append("=")
		    .append(encode(orderBy.getMeaning().getIdentifier()));
	}

	response.sendRedirect(stringBuilder.toString());
    }

    private String encode(String s) throws UnsupportedEncodingException {
	return URLEncoder.encode(s, "UTF-8");
    }

    private void setComputers(HttpServletRequest request, List<ComputerDTO> computers) {
	request.setAttribute(PARAMETER_COMPUTERS, computers);
    }

    private void setNumberOfComputers(HttpServletRequest request, long numberOfComputers) {
	request.setAttribute(PARAMETER_NUMBER_OF_COMPUTERS, numberOfComputers);
    }

}
