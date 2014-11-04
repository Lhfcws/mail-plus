package edu.sysu.lhfcws.mailplus.client.ui.event.callback;

import edu.sysu.lhfcws.mailplus.client.ui.framework.window.ComposeEmailWindow;
import edu.sysu.lhfcws.mailplus.client.ui.framework.window.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;

/**
 * @author lhfcws
 * @time 14-11-1.
 */
public class ComposeEmailWindowCallback implements Callback {
    @Override
    public void callback(AWTEvent _event) {
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    ComposeEmailWindow.getInstance().start();
                    MainWindow.getInstance().setEnabled(false);
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
