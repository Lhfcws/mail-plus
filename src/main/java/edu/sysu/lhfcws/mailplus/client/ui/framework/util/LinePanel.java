package edu.sysu.lhfcws.mailplus.client.ui.framework.util;

import javax.swing.*;
import java.awt.*;

/**
 * A kind of panel that flows in a line.
 * @author lhfcws
 * @time 14-10-31.
 */
public class LinePanel extends JPanel {
    public LinePanel() {
        this.setBorder(BorderFactory.createEmptyBorder());
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.setFont(Font.getFont(Font.MONOSPACED));
    }
}
