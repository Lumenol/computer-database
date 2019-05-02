package com.excilys.cdb.servlet;

import static org.junit.Assert.fail;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.excilys.cdb.service.ComputerService;

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
    public void testDoGetHttpServletRequestHttpServletResponse() {
	Mockito.when(mockRequest.getParameter("page")).thenReturn("3");
	Mockito.when(mockRequest.getParameter("size")).thenReturn("2");

	fail("Not yet implemented");
    }

}
