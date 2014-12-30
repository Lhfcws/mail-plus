package client.test;

import client.preparation.TestPreparation;
import edu.sysu.lhfcws.mailplus.client.ui.framework.window.DownloadEmailsWindow;
import edu.sysu.lhfcws.mailplus.commons.model.MailUser;

import javax.swing.*;

/**
 * @author lhfcws
 * @time 14-11-8.
 */
public class DownloadEmailsWindowTest {

    public static void start() {
        TestPreparation preparation = new TestPreparation();
        preparation.prepareLaunch();
        MailUser mailUser = preparation.prepareMailUser();

        DownloadEmailsWindow.getInstance().setMailUser(mailUser);
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
