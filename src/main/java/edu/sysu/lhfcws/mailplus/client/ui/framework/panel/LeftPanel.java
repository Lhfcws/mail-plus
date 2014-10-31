package edu.sysu.lhfcws.mailplus.client.ui.framework.panel;

import javax.swing.*;
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
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(String.format(" 邮箱 (%s) ", email));
        DefaultMutableTreeNode inboxNode = new DefaultMutableTreeNode(" 收件箱 ");
        DefaultMutableTreeNode sendboxNode = new DefaultMutableTreeNode(" 发件箱 ");
        DefaultMutableTreeNode sendedNode = new DefaultMutableTreeNode(" 已发邮件 ");
        DefaultMutableTreeNode draftNode = new DefaultMutableTreeNode(" 草稿箱 ");
        DefaultMutableTreeNode binNode = new DefaultMutableTreeNode(" 垃圾箱 ");

        root.add(inboxNode);
        root.add(sendboxNode);
        root.add(sendedNode);
        root.add(draftNode);
        root.add(binNode);

        DefaultTreeModel model = new DefaultTreeModel(root);

        JTree tree = new JTree(model);
        tree.setBorder(BorderFactory.createEmptyBorder());
//        tree.setSize(new Dimension(150, 230));

        JScrollPane scrollPane = new JScrollPane(tree);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        this.add(scrollPane);
    }
}
