package com.excilys.cdb.web.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@ComponentScan({"com.excilys.cdb.web.controller.rest"})
public class RestConfiguration {

}
