package edu.sysu.lhfcws.mailplus.client.ui.framework.panel;

import edu.sysu.lhfcws.mailplus.client.background.running.MailOperator;
import edu.sysu.lhfcws.mailplus.client.ui.event.Events;
import edu.sysu.lhfcws.mailplus.client.ui.event.callback.Callback;
import edu.sysu.lhfcws.mailplus.client.ui.event.callback.Function;
import edu.sysu.lhfcws.mailplus.client.ui.framework.util.LinePanel;
import edu.sysu.lhfcws.mailplus.client.ui.framework.util.MultiLinePanel;
import edu.sysu.lhfcws.mailplus.client.ui.framework.window.ComposeEmailWindow;
import edu.sysu.lhfcws.mailplus.client.ui.framework.window.MainWindow;
import edu.sysu.lhfcws.mailplus.commons.controller.EmailController;
import edu.sysu.lhfcws.mailplus.commons.io.BinaryFileReader;
import edu.sysu.lhfcws.mailplus.commons.io.FileLineReader;
import edu.sysu.lhfcws.mailplus.commons.model.Attachment;
import edu.sysu.lhfcws.mailplus.commons.model.Email;
import edu.sysu.lhfcws.mailplus.commons.util.FileChooser;

import javax.swing.*;
import java.awt.AWTEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * The panel contains the components of writing emails.
 *
 * @author lhfcws
 * @time 14-11-1.
 */
public class ComposeEmailPanel extends JPanel {
    private JTextField to, cc, subject;
    private JTextArea content;
    private java.util.List<File> attachmentFiles;
    private JPanel panel;

    public ComposeEmailPanel() {
//        this.panel = new JPanel();
        this.setBorder(BorderFactory.createTitledBorder("Compose Email"));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        addToLine();
        addCcLine();
        addSubjectLine();
        addContentLine();
        addAttachmentArea();
        addButtons();

//        JScrollPane scrollPane = new JScrollPane(panel);
//        scrollPane.createVerticalScrollBar();
//        this.add(scrollPane);
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
        content = new JTextArea(60, 42);
        content.setBorder(BorderFactory.createEtchedBorder());
        contentLine.add(new JScrollPane(content));
        this.add(contentLine);
    }

    private void addAttachmentArea() {
        attachmentFiles = new LinkedList<File>();
        JButton attachBtn = new JButton("attach");
        final ComposeEmailPanel _this = this;
        final MultiLinePanel multiLinePanel = new MultiLinePanel();
        multiLinePanel.setBorder(BorderFactory.createTitledBorder("Attachments"));
        LinePanel linePanel = new LinePanel();
        linePanel.add(new JLabel("Attach your files here."));
        linePanel.add(attachBtn);
        LinePanel linePanel1 = new LinePanel();
        linePanel.add(new JLabel("Attachments:  "));

        Events.onClick(attachBtn, new Callback() {
            @Override
            public void callback(AWTEvent _event) {
                FileChooser fileChooser = new FileChooser();
                File f = fileChooser.getSelectedFile(_this);
                attachmentFiles.add(f);
                multiLinePanel.setVisible(false);
                if (f != null)
                    multiLinePanel.addLine(new JLabel(f.getAbsolutePath()));
                multiLinePanel.setVisible(true);
            }
        });
        multiLinePanel.add(linePanel);
        multiLinePanel.add(linePanel1);

        LinePanel lp = new LinePanel();
        lp.add(multiLinePanel);
        this.add(lp);
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
                email.setTo(asList(to.getText().split(";")));
                if (cc.getText().trim().length() > 0)
                    email.setCc(asList(cc.getText().split(";")));
                email.setSubject(subject.getText());
                email.setContent(content.getText());
                email.setAttachments(getAttachments());
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
                email.setTo(asList(to.getText().split(";")));
                if (cc.getText().trim().length() > 0)
                    email.setCc(asList(cc.getText().split(";")));
                email.setSubject(subject.getText());
                email.setContent(content.getText());
                email.setAttachments(getAttachments());
                email.setDate(new Date());

                new MailOperator().sendEmail(email);

                ComposeEmailWindow.getInstance().close();
            }
        });

        buttonPanel.add(save);
        buttonPanel.add(send);
        this.add(buttonPanel);
    }

    private java.util.List<Attachment> getAttachments() {
        List<Attachment> list = new LinkedList<Attachment>();
        for (File f : attachmentFiles) {
            Attachment attachment = new Attachment();
            attachment.setContentType(f);
            attachment.setFilepath(f.getAbsolutePath());
            attachment.setFilename(f.getName());
            try {
                BinaryFileReader reader = new BinaryFileReader(attachment.getFilepath());
                attachment.setContent(reader.readAllBytes());
                reader.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            list.add(attachment);
        }
        return list;
    }

    private LinkedList<String> asList(String[] ss) {
        LinkedList<String> list = new LinkedList<String>();
        for (String s : ss) {
            s = s.trim();
            if (s.length() > 0)
                list.add(s);
        }
        return list;
    }
}
