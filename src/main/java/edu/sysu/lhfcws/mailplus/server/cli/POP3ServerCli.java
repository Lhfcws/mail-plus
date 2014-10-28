package edu.sysu.lhfcws.mailplus.server.cli;

import edu.sysu.lhfcws.mailplus.commons.util.AdvCli;
import edu.sysu.lhfcws.mailplus.commons.util.CliRunner;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

/**
 * @author lhfcws
 * @time 14-10-21.
 */
public class POP3ServerCli implements CliRunner {

    public static String NAME = "pop3Server";

    @Override
    public Options initOptions() {
        Options options = new Options();
        return options;
    }

    @Override
    public boolean validateOptions(CommandLine cmdLine) {
        return true;
    }

    @Override
    public void start(CommandLine cmdLine) {

    }

    /**
     * Main
     * @param args
     */
    public static void main(String[] args) {
        AdvCli.initRunner(args, NAME, new POP3ServerCli());
    }
}
