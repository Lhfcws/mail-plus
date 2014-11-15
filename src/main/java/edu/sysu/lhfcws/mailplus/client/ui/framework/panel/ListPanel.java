package edu.sysu.lhfcws.mailplus.client.ui.framework.panel;

import edu.sysu.lhfcws.mailplus.client.ui.framework.util.HTMLContainer;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

/**
 * @author lhfcws
 * @time 14-10-30.
 */
public class ListPanel extends JPanel {

    private JScrollPane scrollPane;
    private LinkedList<ListItemPanel> list;

    public ListPanel() {
        this.list = new LinkedList<ListItemPanel>();
        this.scrollPane = new JScrollPane();
        this.scrollPane.createVerticalScrollBar();

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(Color.WHITE);
        this.add(scrollPane);

        clear();
    }

    public void clear() {
        for (ListItemPanel listItemPanel : list) {
            listItemPanel.setVisible(false);
            this.scrollPane.remove(listItemPanel);
        }
        this.scrollPane.removeAll();
        list.clear();
    }

    public void addItem(HTMLContainer container) {
        container.setSize(300, 100);
        ListItemPanel listItemPanel = new ListItemPanel(container);
        this.list.add(listItemPanel);
        this.scrollPane.add(listItemPanel);
    }

    /**
     * Panel for list item.
     */
    public static class ListItemPanel extends JPanel {
        private HTMLContainer htmlContainer;

        public ListItemPanel(HTMLContainer htmlContainer) {
            this.htmlContainer = htmlContainer;
            this.setBorder(BorderFactory.createLineBorder(new Color(10, 10, 10)));
            this.add(htmlContainer);
        }

        public HTMLContainer getHTMLContainer() {
            return htmlContainer;
        }
    }
}
