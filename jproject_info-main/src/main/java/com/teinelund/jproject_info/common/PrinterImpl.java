package com.teinelund.jproject_info.common;

import com.teinelund.jproject_info.command_line_parameters_parser.Parameters;
import org.fusesource.jansi.Ansi;

import javax.inject.Inject;

import static org.fusesource.jansi.Ansi.ansi;

class PrinterImpl implements Printer {

    private Parameters parameters;
    @Inject
    public PrinterImpl(Parameters parameters) {
        this.parameters = parameters;
    }

    @Override
    public void verbose(String message) {
        if (parameters.isVerboseOption()) {
            System.out.println(ansi().fg(Ansi.Color.MAGENTA).a("[VERBOSE] " + message).reset().toString());
        }
    }

    @Override
    public void error(String message) {
        System.err.println(ansi().fg(Ansi.Color.RED).a("[ERROR] " + message + " Type --help to display usage of all options.").reset().toString());
    }
}