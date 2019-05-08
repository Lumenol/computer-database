package com.excilys.cdb.servlet;

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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public class DashboardServlet extends HttpServlet {
    private static final String PARAMETER_SEARCH = "search";
    private static final String DASHBOARD_JSP = "/WEB-INF/views/dashboard.jsp";
    private static final String PARAMETER_COMPUTERS = "computers";
    private static final String PARAMETER_NUMBER_OF_COMPUTERS = "numberOfComputers";
    private static final long serialVersionUID = 1L;
    private static final String DASHBOARD = "dashboard";
    private final Pagination pagination = Pagination.DEFAULT_PAGINATION;
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
            throws IOException {
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
        final String search = getParameterSearch(request);
        final OrderBy orderBy = getOrderBy(request);

        final long numberOfComputers = getComputerCount(search);
        final long pageOutOfRange = pagination.redirectIfPageOutOfRange(request, numberOfComputers);
        if (pageOutOfRange > 0) {
            redirectToPageNumber(response, pageOutOfRange, pageSize);
            return;
        }

        final long offset = (pageIndex - 1) * pageSize;

        final Page page = Page.builder().offset(offset).limit(pageSize).build();
        final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();

        final List<ComputerDTO> computers = getComputers(pageable, search);

        setNumberOfComputers(request, numberOfComputers);
        setComputers(request, computers);
        setSearch(request, search);
        pagination.setPaggingParameters(request, pageIndex, numberOfComputers, pageSize);

        setOrderBy(request, orderBy);

        getServletContext().getRequestDispatcher(DASHBOARD_JSP).forward(request, response);
    }

    private List<ComputerDTO> getComputers(Pageable pageable, String search) {
        final List<Computer> computers;
        if (Objects.isNull(search)) {
            computers = computerService.findAll(pageable);
        } else {
            computers = computerService.search(pageable, search);
        }

        return computers.stream().map(ComputerToComputerDTOMapper.getInstance()::map).collect(Collectors.toList());
    }

    private long getComputerCount(String search) {
        if (Objects.isNull(search)) {
            return computerService.count();
        } else {
            return computerService.countSearch(search);
        }

    }

    private OrderBy getOrderBy(HttpServletRequest request) {
        final String field = request.getParameter("order-by");
        final String meaning = request.getParameter("meaning");

        final OrderBy.OrderByBuilder builder = OrderBy.builder();
        builder.field(OrderBy.Field.byIdentifier(field).orElse(OrderBy.Field.NAME));
        OrderBy.Meaning.byIdentifier(meaning).ifPresent(builder::meaning);

        return builder.build();
    }

    private void setOrderBy(HttpServletRequest request, OrderBy orderBy) {
        final UnaryOperator<String> meaningOfFieldName = name -> {
            if (!orderBy.getField().name().equalsIgnoreCase(name) || orderBy.getMeaning() == OrderBy.Meaning.DESC) {
                return OrderBy.Meaning.ASC.getIdentifier();
            } else {
                return OrderBy.Meaning.DESC.getIdentifier();
            }
        };
        request.setAttribute("orderByUtils", meaningOfFieldName);
        request.setAttribute("orderBy", orderBy);
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
