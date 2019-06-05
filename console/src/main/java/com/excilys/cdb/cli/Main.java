package com.excilys.cdb.cli;

import com.excilys.cdb.cli.configuration.CliConfiguration;
import com.excilys.cdb.cli.view.Cli;
import com.excilys.cdb.persistence.configuration.PersistenceConfiguration;
import com.excilys.cdb.service.configuration.ServiceConfiguration;
import com.excilys.cdb.shared.configuration.SharedConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class Main {

    public static void main(String[] args) {
        AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(CliConfiguration.class, SharedConfiguration.class, ServiceConfiguration.class, PersistenceConfiguration.class);
        final Cli cli = ctx.getBean(Cli.class);
        cli.run();
        ctx.close();
    }

}
