package edu.sysu.lhfcws.mailplus.client.ui.framework.menu;

import edu.sysu.lhfcws.mailplus.client.background.running.MailOperator;
import edu.sysu.lhfcws.mailplus.client.ui.event.Events;
import edu.sysu.lhfcws.mailplus.client.ui.event.callback.Callback;
import edu.sysu.lhfcws.mailplus.client.ui.event.callback.NewMailResponseCallback;
import edu.sysu.lhfcws.mailplus.client.ui.event.callback.WindowExitCallback;
import edu.sysu.lhfcws.mailplus.client.ui.framework.window.ComposeEmailWindow;
import edu.sysu.lhfcws.mailplus.client.ui.framework.window.MainWindow;
import edu.sysu.lhfcws.mailplus.commons.io.res.EmailResponse;
import edu.sysu.lhfcws.mailplus.commons.io.res.Response;
import edu.sysu.lhfcws.mailplus.commons.io.res.ResponseCallback;
import edu.sysu.lhfcws.mailplus.commons.model.Email;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

/**
 * Menu file.
 *
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
        addReceive();
        addSeparator();

        addQuit();
    }

    // ===== JMenuItems

    private void addWirteEmail() {
        JMenuItem item = new JMenuItem("Write");
        item.setMnemonic(KeyEvent.VK_N);
        item.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_N, InputEvent.CTRL_MASK));
        Events.onClick(item, new Callback() {
            @Override
            public void callback(AWTEvent _event) {
                ComposeEmailWindow.getInstance().start();
            }
        });
        this.add(item);
    }

    private void addReceive() {
        JMenuItem item = new JMenuItem("Recv");
        Events.onClick(item, new Callback() {
            @Override
            public void callback(AWTEvent _event) {
                new MailOperator().receiveLatest(
                        MainWindow.getInstance().getToken(), new NewMailResponseCallback());
            }
        });
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
