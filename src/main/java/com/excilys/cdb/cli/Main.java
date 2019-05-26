package com.excilys.cdb.cli;

import com.excilys.cdb.cli.config.CliConfig;
import com.excilys.cdb.cli.view.Cli;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class Main {

    public static void main(String[] args) {
        AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(CliConfig.class);
        final Cli cli = ctx.getBean(Cli.class);
        cli.run();
        ctx.close();
    }

}
