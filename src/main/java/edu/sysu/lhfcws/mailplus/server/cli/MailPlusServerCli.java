package edu.sysu.lhfcws.mailplus.server.cli;

import edu.sysu.lhfcws.mailplus.commons.util.AdvCli;
import edu.sysu.lhfcws.mailplus.commons.util.CliRunner;
import edu.sysu.lhfcws.mailplus.server.serv.MailPlusServer;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

/**
 * @author lhfcws
 * @time 14-11-6.
 */
public class MailPlusServerCli implements CliRunner {
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
        MailPlusServer mailPlusServer = MailPlusServer.getInstance();
        mailPlusServer.start();
    }

    public static void main(String[] args) {
        AdvCli.initRunner(args, "MailPlusServer", new MailPlusServerCli());
    }
}
