package client.test;

import edu.sysu.lhfcws.mailplus.client.background.communication.InternalClient;
import edu.sysu.lhfcws.mailplus.client.ui.framework.window.DownloadEmailsWindow;
import edu.sysu.lhfcws.mailplus.server.serv.MailPlusServer;

import javax.swing.*;

/**
 * @author lhfcws
 * @time 14-11-8.
 */
public class DownloadEmailsWindowTest {

    public static void start() {
        MailPlusServer server = MailPlusServer.getInstance();
        server.start();

        InternalClient client = InternalClient.getInstance();
        client.start();

        DownloadEmailsWindow.getInstance().start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                start();
            }
        });
    }
}
