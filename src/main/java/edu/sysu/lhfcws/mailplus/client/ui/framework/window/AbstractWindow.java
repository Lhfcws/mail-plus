package edu.sysu.lhfcws.mailplus.client.ui.framework.window;

import javax.swing.*;

/**
 * Abstract window.
 * @author lhfcws
 * @time 14-10-31.
 */
public abstract class AbstractWindow extends JFrame {

    protected AbstractWindow(String name) {
        super(name);
        init();
    }

    protected abstract void init();
    public abstract void start();
    public abstract void close();
}
