package com.excilys.cdb.web.configuration;

import com.excilys.cdb.service.service.CompanyServiceImpl;
import com.excilys.cdb.service.service.ComputerServiceImpl;
import com.excilys.cdb.service.service.UserServiceImpl;
import com.excilys.cdb.webapp.configuration.WebConfiguration;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.password.PasswordEncoder;

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

    @Bean
    @Primary
    public UserServiceImpl userService() {
        return Mockito.mock(UserServiceImpl.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return encode(rawPassword).equals(encodedPassword);
            }

            @Override
            public String encode(CharSequence rawPassword) {
                return new StringBuilder(rawPassword).toString();
            }
        };
    }
}
