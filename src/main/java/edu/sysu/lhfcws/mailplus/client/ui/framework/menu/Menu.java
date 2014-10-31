package edu.sysu.lhfcws.mailplus.client.ui.framework.menu;

import javax.swing.*;
import java.awt.*;

/**
 * @author lhfcws
 * @time 14-10-31.
 */
public class Menu extends JMenu {
    public Menu(String name) {
        super(name);
        this.setBorder(BorderFactory.createEtchedBorder());
        this.setBackground(new Color(200, 200, 200));
    }
}
