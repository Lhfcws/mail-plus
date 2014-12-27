package edu.sysu.lhfcws.mailplus.client.cli;

import edu.sysu.lhfcws.mailplus.commons.util.AdvCli;
import edu.sysu.lhfcws.mailplus.commons.util.CliRunner;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

import java.io.File;

/**
 * Initialization in the first using.
 * @author lhfcws
 * @time 14-10-26.
 */
@Deprecated
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
        mkdirIfNExist(".bdb");
        mkdirIfNExist(".sqlite");
    }

    public void mkdirIfNExist(String filename) {
        File f = new File(filename);
        if (!f.exists())
            f.mkdir();
    }

    public static void main(String[] args) {
        AdvCli.initRunner(args, "deploy", new DeployCli());
    }
}
