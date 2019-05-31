package com.excilys.cdb.web.configuration;

import com.excilys.cdb.service.CompanyServiceImpl;
import com.excilys.cdb.service.ComputerServiceImpl;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

@Configuration
@Import(WebConfiguration.class)
public class WebConfigurationTest {
    @Bean
    @Primary
    public ComputerServiceImpl computerService() {
        return Mockito.mock(ComputerServiceImpl.class);
    }

    @Bean
    @Primary
    public CompanyServiceImpl companyService() {
        return Mockito.mock(CompanyServiceImpl.class);
    }
}
