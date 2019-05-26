package com.excilys.cdb.persistence.config;

import com.zaxxer.hikari.HikariConfig;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan("com.excilys.cdb.persistence.database")
@Import(PersistenceConfig.class)
public class PersistenceConfigTest {
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
}
