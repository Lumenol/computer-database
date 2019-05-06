package com.excilys.cdb.main;

import com.excilys.cdb.ui.Cli;

public class Main {

    public static void main(String[] args) {
        final Cli cli = new Cli(System.in, System.out);
        cli.run();
    }

}
