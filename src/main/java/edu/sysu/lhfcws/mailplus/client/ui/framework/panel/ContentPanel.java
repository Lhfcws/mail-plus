package edu.sysu.lhfcws.mailplus.client.ui.framework.panel;

import edu.sysu.lhfcws.mailplus.client.ui.framework.util.HTMLContainer;

import javax.swing.*;
import java.awt.*;

/**
 * @author lhfcws
 * @time 14-10-30.
 */
public class ContentPanel extends JPanel {
    public ContentPanel() {
        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(layout);
        this.setBackground(Color.DARK_GRAY);
    }

    public void clear() {
        this.removeAll();
    }

    public void addContentBox(HTMLContainer contentBox) {
        this.add(contentBox);
    }
}
