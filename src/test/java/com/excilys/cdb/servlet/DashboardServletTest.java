package com.excilys.cdb.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.excilys.cdb.database.UTDatabase;
import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.mapper.dto.ComputerToComputerDTOMapper;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.page.OrderBy;
import com.excilys.cdb.persistence.page.Page;
import com.excilys.cdb.persistence.page.Pageable;
import com.excilys.cdb.service.ComputerService;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ComputerService.class)
public class DashboardServletTest {

    private HttpServletRequest mockRequest;
    private HttpServletResponse mockResponse;

    private ComputerService mockComputerService;

    private DashboardServlet dashboardServlet;

    @Before
    public void setUp() {
	mockRequest = Mockito.mock(HttpServletRequest.class);
	mockResponse = Mockito.mock(HttpServletResponse.class);
	mockComputerService = Mockito.mock(ComputerService.class);
	PowerMockito.mockStatic(ComputerService.class);

	Mockito.when(ComputerService.getInstance()).thenReturn(mockComputerService);

	dashboardServlet = Mockito.spy(new DashboardServlet());

	final ServletContext mockServletContext = Mockito.mock(ServletContext.class);
	final RequestDispatcher mockRequestDispatcher = Mockito.mock(RequestDispatcher.class);
	Mockito.when(mockServletContext.getRequestDispatcher(Mockito.anyString())).thenReturn(mockRequestDispatcher);
	Mockito.doReturn(mockServletContext).when(dashboardServlet).getServletContext();
    }

    @Test
    public void getPage2Size10() throws ServletException, IOException {
	Mockito.when(mockRequest.getParameter("page")).thenReturn("2");
	Mockito.when(mockRequest.getParameter("size")).thenReturn("10");
	final long numberOfComputer = 500L;
	Mockito.when(mockComputerService.count()).thenReturn(numberOfComputer);

	final Page page = Page.builder().page(2).limit(10).build();
	final OrderBy orderBy = OrderBy.builder().field(OrderBy.Field.NAME).build();
	final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();

	final List<Computer> computers = UTDatabase.getInstance().findAllComputers(pageable);
	Mockito.when(mockComputerService.findAll(pageable)).thenReturn(computers);

	dashboardServlet.doGet(mockRequest, mockResponse);

	Mockito.verify(mockRequest).setAttribute("numberOfComputers", numberOfComputer);
	final List<ComputerDTO> computersDTO = computers.stream().map(ComputerToComputerDTOMapper.getInstance()::map)
		.collect(Collectors.toList());
	Mockito.verify(mockRequest).setAttribute("computers", computersDTO);
	Mockito.verify(mockRequest).setAttribute("previous", 1L);
	Mockito.verify(mockRequest).setAttribute("next", 3L);
	Mockito.verify(mockRequest).setAttribute("pages", Arrays.asList(1L, 2L, 3L, 4L, 5L));

	Mockito.verify(mockRequest).setAttribute("size", 10L);
	Mockito.verify(mockRequest).setAttribute("page", 2L);
    }

    @Test
    public void getPageWithSearchApple() throws ServletException, IOException {
	final String search = "Apple";
	Mockito.when(mockRequest.getParameter("search")).thenReturn(search);
	final long numberOfComputer = 10L;
	Mockito.when(mockComputerService.countSearch(search)).thenReturn(numberOfComputer);

	final List<Computer> computers = new ArrayList<>();
	final UTDatabase database = UTDatabase.getInstance();
	computers.add(database.findComputerById(1));
	computers.add(database.findComputerById(6));
	computers.add(database.findComputerById(7));
	computers.add(database.findComputerById(8));
	computers.add(database.findComputerById(9));
	computers.add(database.findComputerById(10));
	computers.add(database.findComputerById(11));
	computers.add(database.findComputerById(12));
	computers.add(database.findComputerById(13));
	computers.add(database.findComputerById(16));
	computers.add(database.findComputerById(17));

	final Page page = Page.builder().page(1).limit(50).build();
	final OrderBy orderBy = OrderBy.builder().field(OrderBy.Field.NAME).build();
	final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();

	Mockito.when(mockComputerService.search(pageable, search)).thenReturn(computers);

	dashboardServlet.doGet(mockRequest, mockResponse);

	Mockito.verify(mockRequest).setAttribute("numberOfComputers", numberOfComputer);
	final List<ComputerDTO> computersDTO = computers.stream().map(ComputerToComputerDTOMapper.getInstance()::map)
		.collect(Collectors.toList());
	Mockito.verify(mockRequest).setAttribute("computers", computersDTO);
	Mockito.verify(mockRequest).setAttribute("pages", Arrays.asList(1L));
	Mockito.verify(mockRequest).setAttribute("search", search);

	Mockito.verify(mockRequest).setAttribute("size", 50L);
	Mockito.verify(mockRequest).setAttribute("page", 1L);
    }

}
