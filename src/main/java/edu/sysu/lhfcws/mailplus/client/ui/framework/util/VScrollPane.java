package edu.sysu.lhfcws.mailplus.client.ui.framework.util;

import javax.swing.*;

/**
 * @author lhfcws
 * @time 14-12-21.
 */
public class VScrollPane extends JScrollPane {
    public VScrollPane(JComponent component) {
        super(component);
        this.createVerticalScrollBar();
    }
}
