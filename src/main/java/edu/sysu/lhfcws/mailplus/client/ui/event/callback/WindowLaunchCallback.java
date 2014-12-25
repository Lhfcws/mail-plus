package edu.sysu.lhfcws.mailplus.client.ui.event.callback;

import edu.sysu.lhfcws.mailplus.client.ui.framework.window.MainWindow;

import java.awt.*;

/**
 * @author lhfcws
 * @time 14-12-25.
 */
public class WindowLaunchCallback implements Callback {
    @Override
    public void callback(AWTEvent _event) {
        MainWindow.getInstance().start();
    }
}
