package com.excilys.cdb.shared.configuration;

import com.excilys.cdb.shared.mapper.FindCompanyById;
import com.excilys.cdb.shared.validator.CompanyExistById;
import com.excilys.cdb.shared.validator.ComputerExistById;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import static org.mockito.Mockito.mock;

@Configuration
@Import(SharedConfiguration.class)
public class SharedConfigurationTest {
    @Bean
    public ComputerExistById computerExistById() {
        return mock(ComputerExistById.class);
    }

    @Bean
    public CompanyExistById companyExistById() {
        return mock(CompanyExistById.class);
    }

    @Bean
    public FindCompanyById findCompanyById() {
        return mock(FindCompanyById.class);
    }
}
