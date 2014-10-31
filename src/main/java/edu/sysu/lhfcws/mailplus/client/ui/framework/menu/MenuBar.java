package edu.sysu.lhfcws.mailplus.client.ui.framework.menu;

import javax.swing.*;
import java.awt.*;

/**
 * @author lhfcws
 * @time 14-10-31.
 */
public class MenuBar extends JMenuBar {
    public MenuBar() {
        init();
        this.setBackground(new Color(200, 200, 200));
    }

    private void init() {
        this.add(new FileMenu());
        this.add(new HelpMenu());
    }
}
