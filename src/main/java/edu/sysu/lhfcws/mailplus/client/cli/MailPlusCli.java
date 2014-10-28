package edu.sysu.lhfcws.mailplus.client.cli;

import edu.sysu.lhfcws.mailplus.client.ui.framework.windows.MainWindow;
import edu.sysu.lhfcws.mailplus.commons.util.CliRunner;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

/**
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
                new MainWindow().start();
            }
        });
    }
}
