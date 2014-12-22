package client.test;

import edu.sysu.lhfcws.mailplus.client.ui.framework.panel.ListPanel;
import edu.sysu.lhfcws.mailplus.client.ui.framework.util.HTMLContainer;
import edu.sysu.lhfcws.mailplus.client.util.EmailContentHTML;
import edu.sysu.lhfcws.mailplus.commons.controller.EmailController;
import edu.sysu.lhfcws.mailplus.commons.model.Email;
import edu.sysu.lhfcws.mailplus.commons.util.LogUtil;

import javax.swing.*;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * @author lhfcws
 * @time 14-11-15.
 */
public class ListPanelTest {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createJFrame();
            }
        });
    }

    public static void createJFrame() {
        JFrame frame = new JFrame("ListPanelTest");

        ListPanel listPanel = new ListPanel();
        Collection<Email.EmailStatus> conditions = new HashSet<Email.EmailStatus>();
        conditions.add(Email.EmailStatus.UNREAD);
        conditions.add(Email.EmailStatus.READED);

        List<Email> list = new LinkedList<Email>();
        try {
            list = new EmailController().getEmailListByStatus(conditions);
            LogUtil.debug("Email size: " + list.size());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (int i = list.size() - 1; i >= 0; i--) {
            Email email = list.get(i);
            EmailContentHTML emailContentHTML = new EmailContentHTML(email);
            HTMLContainer container = new HTMLContainer(emailContentHTML.toListItemHTML());
            container.setInformation(emailContentHTML.getEmailString());
            container.setId(email.getId());
            listPanel.addItem(container);
        }

//        listPanel.repaint();
//        listPanel.setVisible(true);

        frame.add(listPanel);

        frame.pack();
        frame.setVisible(true);
        frame.setLocation(300, 300);
        frame.setSize(400, 200);
    }
}
