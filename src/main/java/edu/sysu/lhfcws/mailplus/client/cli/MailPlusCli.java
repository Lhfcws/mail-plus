package edu.sysu.lhfcws.mailplus.client.cli;

import edu.sysu.lhfcws.mailplus.client.background.launch.Launcher;
import edu.sysu.lhfcws.mailplus.client.background.running.Token;
import edu.sysu.lhfcws.mailplus.commons.util.AdvCli;
import edu.sysu.lhfcws.mailplus.commons.util.CliRunner;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

/**
 * Main entrance cli.
 * @author lhfcws
 * @time 14-10-28.
 */
public class MailPlusCli implements CliRunner {
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
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // test
                Token token = new Token();
                token.setTimestamp(System.currentTimeMillis());
                token.setPassword("lhfcws82283086");
                token.setEmail("lhfcws@163.com");
                new Launcher().loginInit(token);

                // run
//                new Launcher().launch();
            }
        });
    }

    public static void main(String[] args) {
        AdvCli.initRunner(args, "mailplus", new MailPlusCli());
    }
}
