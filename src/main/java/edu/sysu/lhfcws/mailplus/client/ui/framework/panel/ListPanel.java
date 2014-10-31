package edu.sysu.lhfcws.mailplus.client.ui.framework.panel;

import edu.sysu.lhfcws.mailplus.client.ui.framework.util.HTMLContainer;

import javax.swing.*;
import java.awt.*;

/**
 * @author lhfcws
 * @time 14-10-30.
 */
public class ListPanel extends JPanel {

    public ListPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(Color.WHITE);
    }

    public void addItem(HTMLContainer item) {
        this.add(item);
    }
}
