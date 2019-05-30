package com.excilys.cdb.service.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.excilys.cdb.service"}, excludeFilters = @ComponentScan.Filter(Configuration.class))
public class ServiceConfig {
}
