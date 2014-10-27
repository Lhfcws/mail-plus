package edu.sysu.lhfcws.mailplus.server.cli;

import edu.sysu.lhfcws.mailplus.server.cli.util.AdvCli;
import edu.sysu.lhfcws.mailplus.server.cli.util.CliRunner;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

/**
 * Initialization in the first using.
 * @author lhfcws
 * @time 14-10-26.
 */
public class DeployCli implements CliRunner {
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
        // mkdir path if not existed

        // create sqlite tables

    }

    public static void main(String[] args) {
        AdvCli.initRunner(args, "deploy", new DeployCli());
    }
}
