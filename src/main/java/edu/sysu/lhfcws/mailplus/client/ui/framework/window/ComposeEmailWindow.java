package edu.sysu.lhfcws.mailplus.client.ui.framework.window;


import edu.sysu.lhfcws.mailplus.client.ui.event.Events;
import edu.sysu.lhfcws.mailplus.client.ui.event.callback.Callback;
import edu.sysu.lhfcws.mailplus.client.ui.event.callback.Function;
import edu.sysu.lhfcws.mailplus.client.ui.framework.util.LinePanel;
import edu.sysu.lhfcws.mailplus.commons.model.Email;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Date;
import java.util.EventListener;
import java.util.HashMap;

/**
 * The window that pops out for user to write email.
 *
 * @author lhfcws
 * @time 14-10-30.
 */
public class ComposeEmailWindow extends JFrame {

    private JTextField to, cc, subject;
    private JTextArea content;

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
        init();

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(new Color(235, 235, 235));
    }

    private void init() {

    }

    private void addToLine() {
        LinePanel subjectLine = new LinePanel();
        subjectLine.add(new JLabel("To:   "));
        to = new JTextField();
        subjectLine.add(to);
        this.add(subjectLine);
    }

    private void addCcLine() {
        LinePanel subjectLine = new LinePanel();
        subjectLine.add(new JLabel("Cc:   "));
        cc = new JTextField();
        subjectLine.add(cc);
        this.add(subjectLine);
    }

    private void addSubjectLine() {
        LinePanel subjectLine = new LinePanel();
        subjectLine.add(new JLabel("Subject:   "));
        subject = new JTextField();
        subjectLine.add(subject);
        this.add(subjectLine);
    }

    private void addContentLine() {
        LinePanel contentLine = new LinePanel();
        contentLine.add(new JLabel("Content:   "));
        content = new JTextArea();
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
            }
        });

        buttonPanel.add(save);
        buttonPanel.add(send);
        this.add(buttonPanel);
    }
}
