package com.excilys.cdb.cli;

import com.excilys.cdb.cli.config.CliConfig;
import com.excilys.cdb.cli.view.Cli;
import com.excilys.cdb.persistence.config.PersistenceConfig;
import com.excilys.cdb.service.config.ServiceConfig;
import com.excilys.cdb.shared.config.SharedConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class Main {

    public static void main(String[] args) {
        AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(CliConfig.class, SharedConfig.class, ServiceConfig.class, PersistenceConfig.class);
        final Cli cli = ctx.getBean(Cli.class);
        cli.run();
        ctx.close();
    }

}
