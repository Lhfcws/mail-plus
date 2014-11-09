package client.test;

import edu.sysu.lhfcws.mailplus.client.ui.framework.window.MainWindow;

/**
 * @author lhfcws
 * @time 14-11-6.
 */
public class FakeMainWindow {
    public static void start() {
        MainWindow.getInstance().start();
        MainWindow.getInstance().addMailbox("lhfcws@163.com");
    }

    public static void main(String[] args) {
        start();
    }
}
