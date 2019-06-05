package com.excilys.cdb.persistence.configuration;

import com.excilys.cdb.shared.configuration.SharedConfiguration;
import com.zaxxer.hikari.HikariConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

import java.util.Properties;

@Configuration
@Import({PersistenceConfiguration.class, SharedConfiguration.LogExceptionConfiguration.class})
public class PersistenceConfigurationTest {
    @Bean
    @Primary
    public HikariConfig hikariConfig() {
        final HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setUsername("test");
        hikariConfig.setPassword("test");
        hikariConfig.setDriverClassName("org.h2.Driver");
        hikariConfig.setJdbcUrl("jdbc:h2:mem:db;DB_CLOSE_DELAY=-1");
        return hikariConfig;
    }

    @Bean
    @Primary
    public Properties jpaProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "create");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        return properties;
    }

}
