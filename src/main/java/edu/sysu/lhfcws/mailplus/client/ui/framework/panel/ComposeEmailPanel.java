package edu.sysu.lhfcws.mailplus.client.ui.framework.panel;

import edu.sysu.lhfcws.mailplus.client.background.running.MailPlusMailOperator;
import edu.sysu.lhfcws.mailplus.client.ui.event.Events;
import edu.sysu.lhfcws.mailplus.client.ui.event.callback.Function;
import edu.sysu.lhfcws.mailplus.client.ui.framework.window.ComposeEmailWindow;
import edu.sysu.lhfcws.mailplus.client.ui.framework.window.MainWindow;
import edu.sysu.lhfcws.mailplus.commons.controller.EmailController;
import edu.sysu.lhfcws.mailplus.commons.model.Email;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
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

    public void setReplyEmail(Email email) {
        subject.setText("Re: " + email.getSubject());
        content.setText("\n\n\n\n" + "===========Original Mail============\n\n" + email.getContent());
        to.setText(email.getFromString());
    }

    public void setForwardEmail(Email email) {
        subject.setText("Fw: " + email.getSubject());
        content.setText("\n\n\n\n" + "===========Original Mail============\n\n" + email.getContent());
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
        contentLine.add(new JScrollPane(content));
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

                email.setFrom(MainWindow.getInstance().getToken().getEmail());
                email.setSignature(MainWindow.getInstance().getToken().getEmail());
                email.setTo(Arrays.asList(to.getText().split(";")));
                email.setCc(Arrays.asList(cc.getText().split(";")));
                email.setSubject(subject.getText());
                email.setContent(content.getText());
                email.setDate(new Date());

                try {
                    new EmailController().saveDraft(email);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                ComposeEmailWindow.getInstance().close();
            }
        });

        Events.onClick(send, new Function() {
            @Override
            public void callback(AWTEvent _event) {
                Email email = new Email();

                email.setFrom(MainWindow.getInstance().getToken().getEmail());
                email.setSignature(MainWindow.getInstance().getToken().getEmail());
                email.setTo(Arrays.asList(to.getText().split(";")));
                email.setCc(Arrays.asList(cc.getText().split(";")));
                email.setSubject(subject.getText());
                email.setContent(content.getText());
                email.setDate(new Date());

                new MailPlusMailOperator().sendEmail(email);

                ComposeEmailWindow.getInstance().close();
            }
        });

        buttonPanel.add(save);
        buttonPanel.add(send);
        this.add(buttonPanel);
    }
}
