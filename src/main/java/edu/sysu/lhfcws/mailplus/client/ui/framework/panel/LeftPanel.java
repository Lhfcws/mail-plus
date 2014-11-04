package edu.sysu.lhfcws.mailplus.client.ui.framework.panel;

import edu.sysu.lhfcws.mailplus.client.ui.event.Events;
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
//        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
//        this.setLayout(layout);
        this.setBackground(Color.WHITE);
    }

    public void addMailbox(String email) {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(String.format(" Mailbox (%s) ", email));
        DefaultMutableTreeNode inboxNode = new DefaultMutableTreeNode(" Inbox ");
        DefaultMutableTreeNode sendboxNode = new DefaultMutableTreeNode(" Sendbox ");
        DefaultMutableTreeNode sendedNode = new DefaultMutableTreeNode(" Sended Mails ");
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

        root.add(inboxNode);
        root.add(sendboxNode);
        root.add(sendedNode);
        root.add(draftNode);
        root.add(binNode);

        DefaultTreeModel model = new DefaultTreeModel(root);

        final JTree tree = new JTree(model);
        tree.setBorder(BorderFactory.createEmptyBorder());
        tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode selectedNode =
                        (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                switcher.decide(selectedNode.toString());
            }
        });
//        tree.setSize(new Dimension(150, 230));

        JScrollPane scrollPane = new JScrollPane(tree);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        this.add(scrollPane);
    }
}
