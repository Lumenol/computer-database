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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {WebConfigurationTest.class, WebMvcConfiguration.class, SharedConfiguration.class})
@WebAppConfiguration
public class AddComputerControllerTest {
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
        final MockHttpServletRequestBuilder post = post("/computers/add");
        post.param("name", "Nom du computer");
        post.param("introduced", "2019-10-10");
        post.param("discontinued", "2020-10-10");
        mockMvc.perform(post).andExpect(status().isOk());
    }
}
