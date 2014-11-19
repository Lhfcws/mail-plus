package edu.sysu.lhfcws.mailplus.client.ui.framework.panel;

import edu.sysu.lhfcws.mailplus.client.ui.event.Events;
import edu.sysu.lhfcws.mailplus.client.ui.event.callback.Callback;
import edu.sysu.lhfcws.mailplus.client.ui.framework.util.HTMLContainer;
import edu.sysu.lhfcws.mailplus.client.ui.framework.window.ComposeEmailWindow;
import edu.sysu.lhfcws.mailplus.client.ui.framework.window.MainWindow;
import edu.sysu.lhfcws.mailplus.commons.controller.EmailController;
import edu.sysu.lhfcws.mailplus.commons.model.Email;
import edu.sysu.lhfcws.mailplus.commons.util.CommonUtil;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

/**
 * @author lhfcws
 * @time 14-10-30.
 */
public class ContentPanel extends JPanel {
    public ContentPanel() {
        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(layout);
        this.setBackground(Color.DARK_GRAY);
    }

    public void clear() {
        this.removeAll();
    }

    public void addContentBox(HTMLContainer contentBox) {
        this.add(new JScrollPane(contentBox));
        addButtons(contentBox);

    }

    private void addButtons(HTMLContainer contentBox) {
        final Email email = CommonUtil.GSON.fromJson(contentBox.getInformation(), Email.class);
        LinePanel linePanel = new LinePanel();

        JButton replyBtn = new JButton("reply");
        JButton deleteBtn = new JButton("delete");

        Events.onClick(deleteBtn, new Callback() {
            @Override
            public void callback(AWTEvent _event) {
                EmailController emailController = new EmailController();
                try {
                    emailController.deleteEmail(email.getMailID());
                    JOptionPane.showMessageDialog(null, "Email deleted successfully.");
                    clear();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        Events.onClick(replyBtn, new Callback() {
            @Override
            public void callback(AWTEvent _event) {
                ComposeEmailWindow.getInstance().setReplyEmail(email);
                ComposeEmailWindow.getInstance().start();
            }
        });

        linePanel.add(replyBtn);
        linePanel.add(deleteBtn);
        linePanel.setSize(400, 50);

        this.add(linePanel);
    }
}
