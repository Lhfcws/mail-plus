package edu.sysu.lhfcws.mailplus.client.ui.framework.window;

import edu.sysu.lhfcws.mailplus.client.ui.framework.panel.LoginPanel;

import javax.swing.*;
import java.awt.*;

/**
 * @author lhfcws
 * @time 14-11-1.
 */
public class LoginWindow extends AbstractWindow {
    private static LoginWindow _window;
    private LoginPanel loginPanel;

    public static LoginWindow getInstance() {
        if (_window == null) {
            synchronized (LoginWindow.class) {
                if (_window == null) {
                    _window = new LoginWindow();
                }
            }
        }

        return _window;
    }

    private LoginWindow() {
        super("MailPlus Login");
    }

    @Override
    protected void init() {
        loginPanel = new LoginPanel();

        this.add(loginPanel);
        this.setBackground(new Color(200, 200, 200));
        this.setLocation(600, 300);
    }

    @Override
    public void start() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 400);
        this.pack();
        this.setVisible(true);

        loginPanel.setSize(400, 350);
        loginPanel.setVisible(true);
    }

    @Override
    public void close() {
        _window.dispose();
        _window = null;
    }

    // ===== Main Test
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LoginWindow.getInstance().start();
            }
        });
    }
}
