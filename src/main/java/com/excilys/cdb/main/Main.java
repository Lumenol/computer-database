package com.excilys.cdb.main;

import com.excilys.cdb.config.AppConfig;
import com.excilys.cdb.ui.Cli;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class Main {

    public static void main(String[] args) {
        AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        final Cli cli = ctx.getBean(Cli.class);
        cli.run();
        ctx.close();
    }

}
