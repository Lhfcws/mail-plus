package edu.sysu.lhfcws.mailplus.client.ui.framework.panel;

import edu.sysu.lhfcws.mailplus.client.background.running.MailOperator;
import edu.sysu.lhfcws.mailplus.client.ui.event.Events;
import edu.sysu.lhfcws.mailplus.client.ui.event.callback.Callback;
import edu.sysu.lhfcws.mailplus.client.ui.framework.util.HTMLContainer;
import edu.sysu.lhfcws.mailplus.client.ui.framework.util.LinePanel;
import edu.sysu.lhfcws.mailplus.client.ui.framework.util.MultiLinePanel;
import edu.sysu.lhfcws.mailplus.client.ui.framework.util.VScrollPane;
import edu.sysu.lhfcws.mailplus.client.ui.framework.window.ComposeEmailWindow;
import edu.sysu.lhfcws.mailplus.client.ui.framework.window.MainWindow;
import edu.sysu.lhfcws.mailplus.commons.io.FileOutputer;
import edu.sysu.lhfcws.mailplus.commons.model.Attachment;
import edu.sysu.lhfcws.mailplus.commons.model.Email;
import edu.sysu.lhfcws.mailplus.commons.util.CommonUtil;
import edu.sysu.lhfcws.mailplus.commons.util.FileChooser;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * The panel contains detailed email contents.
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
        this.add(new VScrollPane(contentBox));

        addAttachments(contentBox);
        addButtons(contentBox);
    }

    private void addAttachments(HTMLContainer contentBox) {
        final Email email = CommonUtil.GSON.fromJson(contentBox.getInformation(), Email.class);
        int size = email.getAttachments().size();
        if (size == 0) return;
        final ContentPanel _this = this;

        MultiLinePanel multiLinePanel = new MultiLinePanel();
        for (int i = 0; i < size; i++) {
            LinePanel linePanel = new LinePanel();
            final Attachment attachment = email.getAttachments().get(i);

            JButton dlBtn = new JButton("Download");
            JLabel filename = new JLabel(attachment.getFilename() + "   ");

            Events.onClick(dlBtn, new Callback() {
                @Override
                public void callback(AWTEvent _event) {
                    FileChooser fileChooser = new FileChooser();
                    String path = fileChooser.getSavePathByUser(_this);
                    try {
                        FileOutputer fileOutputer = new FileOutputer(path + "/" + attachment.getFilename());
                        fileOutputer.write(attachment.getContent());
                        fileOutputer.close();
                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(_this, "Cannot save file.");
                    }
                }
            });

            linePanel.add(filename);
            linePanel.add(dlBtn);
            multiLinePanel.add(linePanel);
        }

        this.add(new VScrollPane(multiLinePanel));
    }

    private void addButtons(HTMLContainer contentBox) {
        final Email email = CommonUtil.GSON.fromJson(contentBox.getInformation(), Email.class);
        LinePanel linePanel = new LinePanel();

        JButton replyBtn = new JButton("reply");
        JButton deleteBtn = new JButton("delete");
        JButton forwardBtn = new JButton("forward");

        Events.onClick(deleteBtn, new Callback() {
            @Override
            public void callback(AWTEvent _event) {
                new MailOperator().deleteEmail(email);
                JOptionPane.showMessageDialog(null, "Mail is successfully deleted.");
                MainWindow.getInstance().refreshAfterDelete(email);
            }
        });

        Events.onClick(replyBtn, new Callback() {
            @Override
            public void callback(AWTEvent _event) {
                ComposeEmailWindow.getInstance().setReplyEmail(email);
                ComposeEmailWindow.getInstance().start();
            }
        });

        Events.onClick(forwardBtn, new Callback() {
            @Override
            public void callback(AWTEvent _event) {
                ComposeEmailWindow.getInstance().setForwardEmail(email);
                ComposeEmailWindow.getInstance().start();
            }
        });

        linePanel.add(replyBtn);
        linePanel.add(forwardBtn);
        linePanel.add(deleteBtn);
        linePanel.setSize(400, 50);

        this.add(linePanel);
    }
}
