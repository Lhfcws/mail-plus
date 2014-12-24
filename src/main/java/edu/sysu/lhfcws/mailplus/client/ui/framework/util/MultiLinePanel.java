package edu.sysu.lhfcws.mailplus.client.ui.framework.util;

import javax.swing.*;
import java.awt.*;

/**
 * Panel that contains multi lines.
 * @author lhfcws
 * @time 14-12-20.
 */
public class MultiLinePanel extends JPanel {
    public MultiLinePanel() {
        this.setBorder(BorderFactory.createEmptyBorder());
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.setFont(Font.getFont(Font.MONOSPACED));
    }

    public void addLine(JComponent component) {
        LinePanel linePanel = new LinePanel();
        linePanel.add(component);
        this.add(component);
    }
}
