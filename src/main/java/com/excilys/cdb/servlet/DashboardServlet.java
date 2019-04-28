package com.excilys.cdb.servlet;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.mapper.dto.ComputerToComputerDTOMapper;
import com.excilys.cdb.service.ComputerService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class DashboardServlet extends HttpServlet {
    public static final int PAGE_SIZE = 50;
    private final ComputerService computerService = ComputerService.getInstance();

    private List<Long> indexOfPages(long pageCurrent, long pageSize, long numberOfEntities) {
        final Set<Long> pages = new TreeSet<>();
        int shift = 0;
        boolean more = true;
        while (pages.size() < 5 && more) {
            more = false;

            final long before = pageCurrent - shift;
            if (before >= 1) {
                pages.add(before);
                more = true;
            }

            final long next = pageCurrent + shift;
            if ((next - 1) * pageSize < numberOfEntities) {
                pages.add(next);
                more = true;
            }

            shift++;
        }
        return new ArrayList<>(pages);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final long pageNumber = getPageNumber(request);

        final long numberOfComputers = computerService.count();
        if (redirectIfPageOutOfRange(response, pageNumber, numberOfComputers)) return;

        final List<ComputerDTO> computers = computerService.findAll((pageNumber - 1) * PAGE_SIZE, PAGE_SIZE).stream().map(ComputerToComputerDTOMapper.getInstance()::map).collect(Collectors.toList());

        setNumberOfComputers(request, numberOfComputers);
        setComputers(request, computers);
        setPaggingParameters(request, pageNumber, numberOfComputers);

        getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
    }

    private boolean redirectIfPageOutOfRange(HttpServletResponse response, long pageNumber, double numberOfComputers) throws IOException {
        long lastPage = (long) Math.ceil(numberOfComputers / PAGE_SIZE);
        if (pageNumber < 1) {
            redirectToPageNumber(response, 1);
            return true;
        }
        if (pageNumber > lastPage) {
            redirectToPageNumber(response, lastPage);
            return true;
        }
        return false;
    }

    private void redirectToPageNumber(HttpServletResponse response, long pageNumber) throws IOException {
        response.sendRedirect("dashboard?page=" + pageNumber);
    }

    private void setPaggingParameters(HttpServletRequest request, long pageCurrent, long numberOfEntities) {
        setPreviousPage(request, pageCurrent);
        setNextPage(request, pageCurrent, numberOfEntities);
        setPagesNumbers(request, pageCurrent, numberOfEntities);
    }

    private void setPagesNumbers(HttpServletRequest request, long pageCurrent, long numberOfEntries) {
        final List<Long> pages = indexOfPages(pageCurrent, PAGE_SIZE, numberOfEntries);
        request.setAttribute("pages", pages);
    }

    private void setComputers(HttpServletRequest request, List<ComputerDTO> computers) {
        request.setAttribute("computers", computers);
    }

    private void setNextPage(HttpServletRequest request, long pageCurrent, long numberOfEntities) {
        if (pageCurrent * PAGE_SIZE < numberOfEntities) {
            request.setAttribute("next", pageCurrent + 1);
        }
    }

    private void setPreviousPage(HttpServletRequest request, long pageCurrent) {
        if (pageCurrent > 1) {
            request.setAttribute("previous", pageCurrent - 1);
        }
    }

    private void setNumberOfComputers(HttpServletRequest request, long numberOfComputers) {
        request.setAttribute("numberOfComputers", numberOfComputers);
    }

    private long getPageNumber(HttpServletRequest request) {
        final String numeroPageParam = request.getParameter("page");
        if (Objects.isNull(numeroPageParam)) {
            return 1;
        }
        try {
            long pageNumber = Long.parseLong(numeroPageParam);
            if (pageNumber >= 1) {
                return pageNumber;
            }
        } catch (NumberFormatException e) {
        }
        return -1;
    }

}
