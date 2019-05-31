package com.excilys.cdb.cli.configuration;

import com.excilys.cdb.cli.controller.Controller;
import com.excilys.cdb.cli.view.Cli;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.excilys.cdb.cli.controller"}, excludeFilters = @ComponentScan.Filter(Configuration.class))
public class CliConfiguration {
    @Bean
    public Cli cli(Controller controller) {
        return new Cli(controller, System.in, System.out);
    }
}
