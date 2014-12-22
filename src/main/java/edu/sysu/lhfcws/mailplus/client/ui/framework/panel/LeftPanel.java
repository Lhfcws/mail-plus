package edu.sysu.lhfcws.mailplus.client.ui.framework.panel;

import edu.sysu.lhfcws.mailplus.client.ui.framework.window.MainWindow;
import edu.sysu.lhfcws.mailplus.commons.util.ConditionSwitcher;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;

/**
 * @author lhfcws
 * @time 14-10-30.
 */
public class LeftPanel extends JPanel {

    public LeftPanel() {
        this.setBackground(Color.WHITE);
    }

    public void addMailbox(String email) {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(email);
        DefaultMutableTreeNode mailboxNode = new DefaultMutableTreeNode(" Mailbox");
        DefaultMutableTreeNode inboxNode = new DefaultMutableTreeNode(" Inbox ");
        DefaultMutableTreeNode sendboxNode = new DefaultMutableTreeNode(" Sendbox ");
        DefaultMutableTreeNode sendedNode = new DefaultMutableTreeNode(" Sended ");
        DefaultMutableTreeNode draftNode = new DefaultMutableTreeNode(" Draft ");
        DefaultMutableTreeNode binNode = new DefaultMutableTreeNode(" Bin ");

        final ConditionSwitcher switcher = new ConditionSwitcher();
        switcher.addBranch(inboxNode.toString(), new Runnable() {
            @Override
            public void run() {
                MainWindow.getInstance().refreshInbox();
            }
        });
        switcher.addBranch(sendboxNode.toString(), new Runnable() {
            @Override
            public void run() {
                MainWindow.getInstance().refreshSendbox();
            }
        });
        switcher.addBranch(sendedNode.toString(), new Runnable() {
            @Override
            public void run() {
                MainWindow.getInstance().refreshSended();
            }
        });
        switcher.addBranch(draftNode.toString(), new Runnable() {
            @Override
            public void run() {
                MainWindow.getInstance().refreshDraft();
            }
        });

        mailboxNode.add(inboxNode);
        mailboxNode.add(sendboxNode);
        mailboxNode.add(sendedNode);
        mailboxNode.add(draftNode);
        mailboxNode.add(binNode);
        root.add(mailboxNode);

        DefaultTreeModel model = new DefaultTreeModel(root);

        final JTree tree = new JTree(model);
        tree.setToolTipText(email);
        tree.setBorder(BorderFactory.createEmptyBorder());
        tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode selectedNode =
                        (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                switcher.decide(selectedNode.toString());
            }
        });

        JScrollPane scrollPane = new JScrollPane(tree);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        this.add(scrollPane);
    }
}
