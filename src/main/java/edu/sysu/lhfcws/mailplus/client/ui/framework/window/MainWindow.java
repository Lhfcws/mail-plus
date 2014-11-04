package edu.sysu.lhfcws.mailplus.client.ui.framework.window;

import edu.sysu.lhfcws.mailplus.client.ui.framework.menu.MenuBar;
import edu.sysu.lhfcws.mailplus.client.ui.framework.panel.ContentPanel;
import edu.sysu.lhfcws.mailplus.client.ui.framework.panel.LeftPanel;
import edu.sysu.lhfcws.mailplus.client.ui.framework.panel.ListPanel;
import edu.sysu.lhfcws.mailplus.client.ui.framework.util.HTMLContainer;
import edu.sysu.lhfcws.mailplus.client.util.EmailContentHTML;
import edu.sysu.lhfcws.mailplus.commons.controller.EmailController;
import edu.sysu.lhfcws.mailplus.commons.model.Email;

import javax.swing.*;
import java.awt.Dimension;
import java.sql.SQLException;
import java.util.*;

/**
 * Main window.
 *
 * @author lhfcws
 * @time 14-10-28.
 */
public class MainWindow extends AbstractWindow {

    private JMenuBar menuBar;
    private JSplitPane splitPane;
    private LeftPanel leftPanel;
    private ListPanel listPanel;
    private ContentPanel contentPanel;

    private static MainWindow _window = null;

    public static MainWindow getInstance() {
        if (_window == null) {
            synchronized (MainWindow.class) {
                if (_window == null) {
                    _window = new MainWindow();
                }
            }
        }

        return _window;
    }

    private MainWindow() {
        super("MailPlus");
    }

    public void addMailbox(String email) {
        leftPanel.addMailbox(email);
    }

    @Override
    protected void init() {
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        leftPanel = new LeftPanel();
        listPanel = new ListPanel();
        contentPanel = new ContentPanel();
    }

    @Override
    public void start() {
        initMenuBar();

        JSplitPane internalSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        internalSplitPane.add(leftPanel);
        internalSplitPane.add(listPanel);
        internalSplitPane.setBorder(BorderFactory.createEmptyBorder());
        internalSplitPane.setEnabled(false);

        splitPane.add(internalSplitPane);
        splitPane.add(contentPanel);
        splitPane.setEnabled(false);

        this.add(splitPane);

        setSize();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(1200, 700));
        this.pack();
        this.setVisible(true);

//        internalSplitPane.setDividerLocation(0.9);
        splitPane.setDividerLocation(0.38);
    }

    @Override
    public void close() {
        _window.dispose();
        _window = null;
    }

    public void refreshListPanel(List<Email> emailList) {
        // ListPanel Reload DB.
        listPanel.setVisible(false);
        listPanel.clear();

        for (Email email : emailList) {
            EmailContentHTML emailContentHTML = new EmailContentHTML(email);
            HTMLContainer container = new HTMLContainer(emailContentHTML.toListItemHTML());
            container.setSize(300, 100);
            listPanel.addItem(container);
        }

        listPanel.repaint();
        listPanel.setVisible(true);
    }

    public void refreshInbox() {
        Collection<Email.EmailStatus> conditions = new HashSet<Email.EmailStatus>();
        conditions.add(Email.EmailStatus.UNREAD);
        conditions.add(Email.EmailStatus.READED);

        List<Email> list = new LinkedList<Email>();
        try {
            list = new EmailController().getEmailListByStatus(conditions);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.refreshListPanel(list);
    }

    public void refreshSendbox() {
        Collection<Email.EmailStatus> conditions = new HashSet<Email.EmailStatus>();
        conditions.add(Email.EmailStatus.SENDING);

        List<Email> list = new LinkedList<Email>();
        try {
            list = new EmailController().getEmailListByStatus(conditions);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.refreshListPanel(list);
    }

    public void refreshDraft() {
        Collection<Email.EmailStatus> conditions = new HashSet<Email.EmailStatus>();
        conditions.add(Email.EmailStatus.DRAFT);

        List<Email> list = new LinkedList<Email>();
        try {
            list = new EmailController().getEmailListByStatus(conditions);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.refreshListPanel(list);
    }

    public void refreshSended() {
        Collection<Email.EmailStatus> conditions = new HashSet<Email.EmailStatus>();
        conditions.add(Email.EmailStatus.SENDED);

        List<Email> list = new LinkedList<Email>();
        try {
            list = new EmailController().getEmailListByStatus(conditions);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.refreshListPanel(list);
    }

    private void initMenuBar() {
        this.setJMenuBar(new MenuBar());
    }

    private void setSize() {
        leftPanel.setSize(new Dimension(180, 700));
        listPanel.setSize(new Dimension(300, 700));
    }

    // ===== Main Test
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainWindow.getInstance().start();
            }
        });
    }
}
