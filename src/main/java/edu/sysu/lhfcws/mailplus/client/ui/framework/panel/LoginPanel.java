package edu.sysu.lhfcws.mailplus.client.ui.framework.panel;

import edu.sysu.lhfcws.mailplus.client.background.launch.Launcher;
import edu.sysu.lhfcws.mailplus.client.background.running.Token;
import edu.sysu.lhfcws.mailplus.client.ui.event.Events;
import edu.sysu.lhfcws.mailplus.client.ui.event.callback.Function;
import edu.sysu.lhfcws.mailplus.client.ui.framework.util.LinePanel;
import edu.sysu.lhfcws.mailplus.client.ui.framework.window.LoginWindow;
import edu.sysu.lhfcws.mailplus.commons.validate.PatternValidater;
import org.apache.commons.lang.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * The panel for login.
 * @author lhfcws
 * @time 14-11-1.
 */
public class LoginPanel extends JPanel {

    public LoginPanel() {
        this.setBorder(BorderFactory.createTitledBorder("Login"));
//        this.setLayout(new FlowLayout());
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
//        this.add(new JButton("test"));

        init();
    }

    public void init() {
        LinePanel emailLine = new LinePanel();
        emailLine.add(new JLabel("MailAddr:  "));
        final JTextField emailField = new JTextField(18);
        emailLine.add(emailField);

        LinePanel passwordLine = new LinePanel();
        passwordLine.add(new JLabel("Password:  "));
        final JPasswordField passwordField = new JPasswordField(18);
        passwordLine.add(passwordField);

        LinePanel buttonLine = new LinePanel();
        final JButton loginBtn = new JButton("Login");
        final JButton exitBtn = new JButton("Exit");

        Events.onClick(exitBtn, new Function() {
            @Override
            public void callback(AWTEvent _event) {
                LoginWindow.getInstance().close();
            }
        });
        Events.onClick(loginBtn, new Function() {
            @Override
            public void callback(AWTEvent _event) {
                Token token = new Token(emailField.getText(),
                        passwordField.getText(), System.currentTimeMillis());
                // validate
                boolean validate = !StringUtils.isEmpty(token.getPassword())
                        && PatternValidater.validateMailAddress(token.getEmail());
                if (!validate) {
                    JOptionPane.showMessageDialog(LoginWindow.getInstance(),
                            "Please input valid email and password.");
                    return;
                }

                boolean res = new Launcher().loginInit(token);
                if (res) {
                    LoginWindow.getInstance().close();
                }
            }
        });

        Events.bindKeyToButton(KeyEvent.VK_ENTER, loginBtn);
        Events.bindKeyToButton(KeyEvent.VK_ESCAPE, exitBtn);

        buttonLine.add(exitBtn);
        buttonLine.add(loginBtn);

        this.add(emailLine);
        this.add(passwordLine);
        this.add(buttonLine);

    }
}
