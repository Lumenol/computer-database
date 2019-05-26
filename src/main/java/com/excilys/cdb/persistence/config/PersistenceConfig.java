package com.excilys.cdb.persistence.config;

import com.excilys.cdb.shared.config.SharedConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.TimeZone;

@Configuration
@ComponentScan(basePackages = {"com.excilys.cdb.persistence.dao", "com.excilys.cdb.persistence.rowmapper"}, excludeFilters = @ComponentScan.Filter(Configuration.class))
@EnableTransactionManagement
@Import({SharedConfig.class})
public class PersistenceConfig {

    @Bean(destroyMethod = "close")
    public HikariDataSource dataSource(HikariConfig configuration) {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        return new HikariDataSource(configuration);
    }

    @Bean
    public HikariConfig hikariConfig() {
        return new HikariConfig("/datasource.properties");
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
