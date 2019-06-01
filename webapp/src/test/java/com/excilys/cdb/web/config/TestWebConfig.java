package com.excilys.cdb.web.config;

import com.excilys.cdb.service.CompanyServiceImpl;
import com.excilys.cdb.service.ComputerServiceImpl;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

@Configuration
@Import(WebConfig.class)
public class TestWebConfig {
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
