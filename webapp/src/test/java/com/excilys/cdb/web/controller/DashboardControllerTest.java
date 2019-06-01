package com.excilys.cdb.web.controller;

import com.excilys.cdb.service.service.ComputerService;
import com.excilys.cdb.shared.configuration.SharedConfiguration;
import com.excilys.cdb.web.configuration.WebConfigurationTest;
import com.excilys.cdb.web.configuration.WebMvcConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {WebConfigurationTest.class, WebMvcConfiguration.class, SharedConfiguration.class})
@WebAppConfiguration
public class DashboardControllerTest {
    private static final String PARAMETER_PAGE = "page";
    private static final String PARAMETER_NUMBER_OF_COMPUTERS = "numberOfComputers";
    final String PARAMETER_PAGES = "pages";
    private WebApplicationContext wac;
    private MockMvc mockMvc;
    private ComputerService mockComputerService;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        Mockito.reset(mockComputerService);
    }

    @Autowired
    public void setMockComputerService(ComputerService mockComputerService) {
        this.mockComputerService = mockComputerService;
    }

    @Autowired
    public void setWac(WebApplicationContext wac) {
        this.wac = wac;
    }

    @Test
    public void testComputers() throws Exception {
        final long count = 10_000L;
        Mockito.when(mockComputerService.count()).thenReturn(count);
        mockMvc.perform(get("/dashboard")).andExpect(status().isOk())
                .andExpect(model().attribute(PARAMETER_PAGE, is(1L)))
                .andExpect(model().attribute(PARAMETER_PAGES, is(Arrays.asList(1L, 2L, 3L, 4L, 5L))))
                .andExpect(model().attribute(PARAMETER_NUMBER_OF_COMPUTERS, count));
    }
}
