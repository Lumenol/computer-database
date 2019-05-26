package com.excilys.cdb.cli.config;

import com.excilys.cdb.cli.controller.Controller;
import com.excilys.cdb.cli.view.Cli;
import com.excilys.cdb.shared.config.SharedConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = {"com.excilys.cdb.cli.controller"}, excludeFilters = @ComponentScan.Filter(Configuration.class))
@Import({SharedConfig.class})
public class CliConfig {
    @Bean
    public Cli cli(Controller controller) {
        return new Cli(controller, System.in, System.out);
    }
}
