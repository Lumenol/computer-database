package com.excilys.cdb.service.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.excilys.cdb.service.service"}, excludeFilters = @ComponentScan.Filter(Configuration.class))
public class ServiceConfiguration {
}
