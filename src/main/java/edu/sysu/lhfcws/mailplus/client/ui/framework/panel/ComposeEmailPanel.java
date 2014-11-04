package edu.sysu.lhfcws.mailplus.client.ui.framework.panel;

import edu.sysu.lhfcws.mailplus.client.ui.event.Events;
import edu.sysu.lhfcws.mailplus.client.ui.event.callback.Function;
import edu.sysu.lhfcws.mailplus.client.ui.framework.window.ComposeEmailWindow;
import edu.sysu.lhfcws.mailplus.commons.model.Email;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Date;

/**
 * @author lhfcws
 * @time 14-11-1.
 */
public class ComposeEmailPanel extends JPanel {
    private JTextField to, cc, subject;
    private JTextArea content;

    public ComposeEmailPanel() {
        this.setBorder(BorderFactory.createTitledBorder("Compose Email"));
//        this.setLayout(new FlowLayout());
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        addToLine();
        addCcLine();
        addSubjectLine();
        addContentLine();
        addButtons();
    }

    private void addToLine() {
        LinePanel linePanel = new LinePanel();
        linePanel.add(new JLabel("To:   "));
        to = new JTextField(44);
        linePanel.add(to);
        this.add(linePanel);
    }

    private void addCcLine() {
        LinePanel linePanel = new LinePanel();
        linePanel.add(new JLabel("Cc:   "));
        cc = new JTextField(44);
        linePanel.add(cc);
        this.add(linePanel);
    }

    private void addSubjectLine() {
        LinePanel linePanel = new LinePanel();
        linePanel.add(new JLabel("Subject: "));
        subject = new JTextField(42);
        linePanel.add(subject);
        this.add(linePanel);
    }

    private void addContentLine() {
        LinePanel contentLine = new LinePanel();
        contentLine.add(new JLabel("Content: "));
        content = new JTextArea(100, 42);
        content.setBorder(BorderFactory.createEtchedBorder());
        contentLine.add(content);
        this.add(contentLine);
    }

    private void addButtons() {
        JButton save = new JButton("Save to draft");
        JButton send = new JButton("Send");
        LinePanel buttonPanel = new LinePanel();

        Events.onClick(save, new Function() {
            @Override
            public void callback(AWTEvent _event) {
                Email email = new Email();
//                email.setFrom();

                email.setTo(Arrays.asList(to.getText().split(";")));
                email.setCc(Arrays.asList(cc.getText().split(";")));
                email.setSubject(subject.getText());
                email.setContent(content.getText());
                email.setDate(new Date());

                ComposeEmailWindow.getInstance().close();
            }
        });

        Events.onClick(send, new Function() {
            @Override
            public void callback(AWTEvent _event) {
                Email email = new Email();
//                email.setFrom();

                email.setTo(Arrays.asList(to.getText().split(";")));
                email.setCc(Arrays.asList(cc.getText().split(";")));
                email.setSubject(subject.getText());
                email.setContent(content.getText());
                email.setDate(new Date());

                ComposeEmailWindow.getInstance().close();
            }
        });

        buttonPanel.add(save);
        buttonPanel.add(send);
        this.add(buttonPanel);
    }
}
