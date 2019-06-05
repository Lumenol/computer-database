package com.excilys.cdb.web.controller;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.service.ComputerService;
import com.excilys.cdb.shared.configuration.SharedConfiguration;
import com.excilys.cdb.shared.pagination.Pageable;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {WebConfigurationTest.class, WebMvcConfiguration.class, SharedConfiguration.class})
@WebAppConfiguration
public class DashboardControllerTest {
    private static final String PARAMETER_PAGE = "page";
    private static final String PARAMETER_NUMBER_OF_COMPUTERS = "numberOfComputers";
    private static final String PARAMETER_COMPUTERS = "computers";
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
        when(mockComputerService.count()).thenReturn(count);
        final Pageable pageable = new Pageable();
        final ArrayList<Computer> computers = new ArrayList<>();
        for (int i = 1; i < 50; i += 5) {
            computers.add(Computer.builder().id((long) i).name("Cpt " + i).build());
            computers.add(Computer.builder().id((long) i + 1).name("Cpt " + i + 1).introduced(LocalDate.ofEpochDay(10 * i)).build());
            computers.add(Computer.builder().id((long) i + 2).name("Cpt " + i + 2).discontinued(LocalDate.ofEpochDay(10 * i)).build());
            computers.add(Computer.builder().id((long) i + 3).name("Cpt " + i + 3).introduced(LocalDate.ofEpochDay(12 * i)).discontinued(LocalDate.ofEpochDay(15 * i)).build());
            final Company manufacturer = Company.builder().id((long) i).name("Company " + i).build();
            computers.add(Computer.builder().id((long) i + 4).name("Cpt " + i + 4).manufacturer(manufacturer).build());
        }

        when(mockComputerService.findAll(pageable)).thenReturn(computers);
        mockMvc.perform(get("/dashboard")).andExpect(status().isOk())
                .andExpect(model().attribute(PARAMETER_PAGE, is(1L)))
                .andExpect(model().attribute(PARAMETER_PAGES, is(Arrays.asList(1L, 2L, 3L, 4L, 5L))))
                .andExpect(model().attribute(PARAMETER_NUMBER_OF_COMPUTERS, count))
                .andExpect(model().attribute(PARAMETER_COMPUTERS, hasSize(computers.size())));
    }
}
