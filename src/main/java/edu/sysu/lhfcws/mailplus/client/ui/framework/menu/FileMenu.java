package edu.sysu.lhfcws.mailplus.client.ui.framework.menu;

import edu.sysu.lhfcws.mailplus.client.ui.event.Events;
import edu.sysu.lhfcws.mailplus.client.ui.event.callback.WindowExitCallback;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

/**
 * @author lhfcws
 * @time 14-10-21.
 */
public class FileMenu extends Menu {
    public FileMenu() {
        super("File");
        init();
    }

    private void init() {
        addWirteEmail();
        addSeparator();

        addSeparator();

        addQuit();
    }

    // ===== JMenuItems

    private void addWirteEmail() {
        JMenuItem item = new JMenuItem("New Email");
        item.setMnemonic(KeyEvent.VK_N);
        item.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_N, InputEvent.CTRL_MASK));
        this.add(item);
    }

    private void addImport() {
        JMenuItem item = new JMenuItem("Import");
        this.add(item);
    }

    private void addExport() {
        JMenuItem item = new JMenuItem("Export");
        this.add(item);
    }

    private void addQuit() {
        JMenuItem item = new JMenuItem("Quit");
        item.setMnemonic(KeyEvent.VK_Q);
        item.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_Q, InputEvent.CTRL_MASK));

        Events.onClick(item, new WindowExitCallback());

        this.add(item);
    }
}
