package com.excilys.cdb.service.config;

import com.excilys.cdb.persistence.config.PersistenceConfig;
import com.excilys.cdb.shared.config.SharedConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = {"com.excilys.cdb.service"}, excludeFilters = @ComponentScan.Filter(Configuration.class))
@Import({PersistenceConfig.class, SharedConfig.class})
public class ServiceConfig {
}
