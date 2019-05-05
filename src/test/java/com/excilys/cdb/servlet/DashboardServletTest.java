package com.excilys.cdb.servlet;

import com.excilys.cdb.database.UTDatabase;
import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.mapper.dto.ComputerToComputerDTOMapper;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.ComputerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ComputerService.class)
public class DashboardServletTest {

    private HttpServletRequest mockRequest;
    private HttpServletResponse mockResponse;

    private ComputerService mockComputerService;

    @Before
    public void etUp() {
        mockRequest = Mockito.mock(HttpServletRequest.class);
        mockResponse = Mockito.mock(HttpServletResponse.class);
        mockComputerService = Mockito.mock(ComputerService.class);
        PowerMockito.mockStatic(ComputerService.class);

        Mockito.when(ComputerService.getInstance()).thenReturn(mockComputerService);
    }

    @Test
    public void testDoGetHttpServletRequestHttpServletResponse() throws ServletException, IOException {
        Mockito.when(mockRequest.getParameter("page")).thenReturn("2");
        Mockito.when(mockRequest.getParameter("size")).thenReturn("10");
        final long numberOfComputer = 500L;
        Mockito.when(mockComputerService.count()).thenReturn(numberOfComputer);

        final List<Computer> computers = UTDatabase.getInstance().findAllComputers(10, 10);

        Mockito.when(mockComputerService.findAll(10, 10)).thenReturn(computers);

        final DashboardServlet dashboardServlet = Mockito.spy(new DashboardServlet());

        final ServletContext mockServletContext = Mockito.mock(ServletContext.class);
        final RequestDispatcher mockRequestDispatcher = Mockito.mock(RequestDispatcher.class);

        Mockito.when(mockServletContext.getRequestDispatcher(Mockito.anyString())).thenReturn(mockRequestDispatcher);
        Mockito.doReturn(mockServletContext).when(dashboardServlet).getServletContext();

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

}
