package edu.sysu.lhfcws.mailplus.client.ui.framework.window;


import edu.sysu.lhfcws.mailplus.client.ui.framework.panel.ComposeEmailPanel;
import edu.sysu.lhfcws.mailplus.commons.model.Email;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * The window that pops out for user to write email.
 *
 * @author lhfcws
 * @time 14-10-30.
 */
public class ComposeEmailWindow extends AbstractWindow {
    private boolean hasStart;
    private ComposeEmailPanel composeEmailPanel;

    private static ComposeEmailWindow _window = null;

    public static ComposeEmailWindow getInstance() {
        if (_window == null) {
            synchronized (ComposeEmailWindow.class) {
                if (_window == null) {
                    _window = new ComposeEmailWindow();
                }
            }
        }

        return _window;
    }

    private ComposeEmailWindow() {
        super("Compose Email");
    }

    @Override
    public void start() {
        if (!hasStart) {
            this.pack();
            this.setVisible(true);
            hasStart = true;
            MainWindow.getInstance().setEnabled(false);
        }
    }

    @Override
    protected void init() {
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                close();
            }

            @Override
            public void windowClosing(WindowEvent e) {
                close();
            }
        });

        composeEmailPanel = new ComposeEmailPanel();
        JScrollPane scrollPane = new JScrollPane(composeEmailPanel);
        scrollPane.createVerticalScrollBar();
        this.add(scrollPane);

        this.hasStart = false;
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        this.setBackground(new Color(235, 235, 235));
    }

    public void setReplyEmail(Email email) {
        composeEmailPanel.setReplyEmail(email);
    }

    public void setForwardEmail(Email email) {
        composeEmailPanel.setForwardEmail(email);
    }

    @Override
    public void close() {
        if (_window != null)
            _window.dispose();
        _window = null;
        hasStart = false;
        MainWindow.getInstance().setEnabled(true);
    }

    // ===== Main Test
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ComposeEmailWindow.getInstance().start();
            }
        });
    }
}
