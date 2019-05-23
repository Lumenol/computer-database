package com.excilys.cdb.controller.web;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.excilys.cdb.config.TestConfig;
import com.excilys.cdb.config.WebMvcConfiguration;
import com.excilys.cdb.service.ComputerService;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { DashboardControllerTest.Config.class, TestConfig.class, WebMvcConfiguration.class })
@WebAppConfiguration
public class DashboardControllerTest {
    private final static String PARAMETER_PAGE = "page";

    @Configuration
    public static class Config {

	@Bean
	@Primary
	public ComputerService computerService() {
	    return Mockito.mock(ComputerService.class);
	}

    }

    private WebApplicationContext wac;

    private MockMvc mockMvc;
    private ComputerService mockComputerService;

    @Before
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
	mockMvc.perform(get("/dashboard")).andExpect(status().isOk())
		.andExpect(model().attribute(PARAMETER_PAGE, is(1L)));
    }

}
