package edu.sysu.lhfcws.mailplus.client.ui.event.callback;

import java.awt.*;

/**
 * Exit window callback.
 * @author lhfcws
 * @time 14-10-31.
 */
public class WindowExitCallback implements Callback {
    @Override
    public void callback(AWTEvent _event) {
        System.exit(0);
    }
}
