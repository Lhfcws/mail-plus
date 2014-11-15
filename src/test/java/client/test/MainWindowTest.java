package client.test;

import edu.sysu.lhfcws.mailplus.client.ui.framework.window.MainWindow;

import javax.swing.*;

/**
 * @author lhfcws
 * @time 14-11-6.
 */
public class MainWindowTest {
    public static void start() {
        MainWindow.getInstance().addMailbox("lhfcws@163.com");
        MainWindow.getInstance().start();
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
