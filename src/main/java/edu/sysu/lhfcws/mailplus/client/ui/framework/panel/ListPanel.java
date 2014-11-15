package edu.sysu.lhfcws.mailplus.client.ui.framework.panel;

import edu.sysu.lhfcws.mailplus.client.ui.event.Events;
import edu.sysu.lhfcws.mailplus.client.ui.event.callback.Callback;
import edu.sysu.lhfcws.mailplus.client.ui.framework.util.HTMLContainer;
import edu.sysu.lhfcws.mailplus.commons.model.Email;
import edu.sysu.lhfcws.mailplus.commons.util.CommonUtil;
import edu.sysu.lhfcws.mailplus.commons.util.LogUtil;
import sun.swing.DefaultLookup;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Vector;

/**
 * @author lhfcws
 * @time 14-10-30.
 */
public class ListPanel extends JPanel {

    private JList jList;
    private JScrollPane scrollPane;
    private Vector<ListItemPanel> list;

    public ListPanel() {
        this.list = new Vector<ListItemPanel>();
        this.jList = new JList<ListItemPanel>(list);
        this.jList.setCellRenderer(ListItemPanel.getCellRenderer());
        this.jList.setBackground(Color.WHITE);
        this.jList.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        this.jList.setEnabled(true);

        this.scrollPane = new JScrollPane(jList);
        this.scrollPane.createVerticalScrollBar();
        this.scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        this.setBackground(Color.WHITE);
        this.add(scrollPane);

    }

    public void clear() {
        for (ListItemPanel listItemPanel : list) {
            listItemPanel.setVisible(false);
        }
        list = new Vector<ListItemPanel>();
        this.jList.setListData(list);
    }

    public void addItem(HTMLContainer container) {
//        container.setSize(300, 100);
        ListItemPanel listItemPanel = new ListItemPanel(container);
        this.list.add(listItemPanel);
    }

    /**
     * Panel for list item.
     */
    public static class ListItemPanel extends HTMLContainer implements ListCellRenderer<ListItemPanel> {
        private static final Border SAFE_NO_FOCUS_BORDER = new EmptyBorder(1, 1, 1, 1);
        private static final Border DEFAULT_NO_FOCUS_BORDER = new EmptyBorder(1, 1, 1, 1);
        protected static Border noFocusBorder = DEFAULT_NO_FOCUS_BORDER;


        protected ListItemPanel() {}

        public ListItemPanel(HTMLContainer htmlContainer) {
            super(htmlContainer.getHtml());

            this.setInformation(htmlContainer.getInformation());
            this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
            this.setBackground(Color.WHITE);
            this.setBorder(BorderFactory.createLineBorder(new Color(10, 10, 10)));
//            this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            this.add(htmlContainer);
            this.setOpaque(true);

            final HTMLContainer param = htmlContainer;

            Events.onClick(this, new Callback() {
                @Override
                public void callback(AWTEvent _event) {
                    // Reduct to Email object
                    Email email = CommonUtil.GSON.fromJson(
                            param.getInformation(), Email.class);

                    // Refresh MainWindow:ContentPanel
                    LogUtil.debug("Click Email subject: " + email.getSubject());
                }
            });
        }

        public static ListItemPanel getCellRenderer() {
            return new ListItemPanel();
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends ListItemPanel> list, ListItemPanel value, int index, boolean isSelected, boolean cellHasFocus) {
            value.setComponentOrientation(list.getComponentOrientation());

            Color bg = null;
            Color fg = null;

            JList.DropLocation dropLocation = list.getDropLocation();
            if (dropLocation != null
                    && !dropLocation.isInsert()
                    && dropLocation.getIndex() == index) {

                bg = DefaultLookup.getColor(this, ui, "List.dropCellBackground");
                fg = DefaultLookup.getColor(this, ui, "List.dropCellForeground");

                isSelected = true;
            }

            if (isSelected) {
                value.setBackground(bg == null ? list.getSelectionBackground() : bg);
                value.setForeground(fg == null ? list.getSelectionForeground() : fg);
            }
            else {
                value.setBackground(list.getBackground());
                value.setForeground(list.getForeground());
            }

            value.setEnabled(list.isEnabled());
            value.setFont(list.getFont());

//            Border border = null;
//            if (cellHasFocus) {
//                if (isSelected) {
//                    border = DefaultLookup.getBorder(this, ui, "List.focusSelectedCellHighlightBorder");
//                }
//                if (border == null) {
//                    border = DefaultLookup.getBorder(this, ui, "List.focusCellHighlightBorder");
//                }
//            } else {
//                border = getNoFocusBorder();
//            }
//            value.setBorder(border);

            return value;
        }

        private Border getNoFocusBorder() {
            Border border = DefaultLookup.getBorder(this, ui, "List.cellNoFocusBorder");
            if (System.getSecurityManager() != null) {
                if (border != null) return border;
                return SAFE_NO_FOCUS_BORDER;
            } else {
                if (border != null &&
                        (noFocusBorder == null ||
                                noFocusBorder == DEFAULT_NO_FOCUS_BORDER)) {
                    return border;
                }
                return noFocusBorder;
            }
        }
    }
}
