package edu.sysu.lhfcws.mailplus.client.ui.framework.menu;

import edu.sysu.lhfcws.mailplus.client.ui.event.Events;
import edu.sysu.lhfcws.mailplus.client.ui.event.callback.AboutCallback;

import javax.swing.*;

/**
 * Menu help.
 * @author lhfcws
 * @time 14-10-31.
 */
public class HelpMenu extends Menu {
    public HelpMenu() {
        super("Help");
        init();
    }

    private void init() {
        addAbout();
    }

    // ===== JMenuItems
    private void addAbout() {
        JMenuItem item = new JMenuItem("About");

        Events.onClick(item, new AboutCallback());

        this.add(item);
    }
}
