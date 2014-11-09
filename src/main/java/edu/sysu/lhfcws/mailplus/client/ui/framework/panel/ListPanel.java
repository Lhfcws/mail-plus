package edu.sysu.lhfcws.mailplus.client.ui.framework.panel;

import edu.sysu.lhfcws.mailplus.client.ui.framework.util.HTMLContainer;

import javax.swing.*;
import java.awt.*;

/**
 * @author lhfcws
 * @time 14-10-30.
 */
public class ListPanel extends JPanel {

    private DefaultListModel<HTMLContainer> listModel;
    private JList<HTMLContainer> jList;

    public ListPanel() {
        this.jList = new JList<HTMLContainer>();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(Color.WHITE);
        this.add(jList);

        clear();
    }

    public void clear() {
        if (listModel != null)
            listModel.clear();
        listModel = new DefaultListModel<HTMLContainer>();
        jList.setModel(listModel);
    }

    public void addItem(HTMLContainer container) {
        container.setSize(300, 100);
        listModel.addElement(container);
    }
}
