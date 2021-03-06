package edu.sysu.lhfcws.mailplus.client.ui.framework.panel;

import edu.sysu.lhfcws.mailplus.client.ui.event.Events;
import edu.sysu.lhfcws.mailplus.client.ui.event.callback.Callback;
import edu.sysu.lhfcws.mailplus.client.ui.framework.util.HTMLContainer;
import edu.sysu.lhfcws.mailplus.client.ui.framework.window.MainWindow;
import edu.sysu.lhfcws.mailplus.commons.controller.EmailController;
import edu.sysu.lhfcws.mailplus.commons.model.Email;
import edu.sysu.lhfcws.mailplus.commons.util.CommonUtil;
import edu.sysu.lhfcws.mailplus.commons.util.LogUtil;
import sun.swing.DefaultLookup;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

/**
 * The panel that cotains a lis tof emails.
 * @author lhfcws
 * @time 14-10-30.
 */
public class ListPanel extends JPanel {

    private JList jList;
    private JScrollPane scrollPane;
    private Vector<ListPanelItem> list;

    public ListPanel() {
        this.list = new Vector<ListPanelItem>();
        this.jList = new JList<ListPanelItem>(list);
        this.jList.setCellRenderer(ListPanelItem.getCellRenderer());
        this.jList.setBackground(Color.WHITE);
        this.jList.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        this.jList.setEnabled(true);
        this.jList.setBorder(BorderFactory.createEmptyBorder());
        this.jList.setFixedCellWidth(270);
        this.jList.setVisibleRowCount(14);

        Events.onClick(this.jList, new Callback() {
            @Override
            public void callback(AWTEvent _event) {
                ListPanelItem item = (ListPanelItem) jList.getSelectedValue();
                if (item != null) {
                    Email email = CommonUtil.GSON.fromJson(item.getInformation(), Email.class);

                    LogUtil.debug("Selected email ID is : " + email.getId());
                    MainWindow.getInstance().refreshContentPanel(email);
                }
            }
        });

        this.scrollPane = new JScrollPane(jList);
        this.scrollPane.setBorder(BorderFactory.createEmptyBorder());
//        this.scrollPane.createVerticalScrollBar();
//        this.scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        this.setBackground(Color.WHITE);
        this.add(scrollPane);
//        this.add(jList);
    }

    public void clear() {
        this.list.clear();
    }

    public void addItem(HTMLContainer container) {
        ListPanelItem listPanelItem = new ListPanelItem(container);
        this.list.add(listPanelItem);
    }

    public void delItem(Email email) {
        int size = this.list.size();
        for (int i = 0; i < size; i++) {
            if (this.list.get(i).getId() == email.getId()) {
                this.list.remove(i);
                break;
            }
        }
    }

    public void update() {
        this.jList.updateUI();
        this.setVisible(true);
    }

    /**
     * Panel for list item.
     */
    public static class ListPanelItem extends HTMLContainer implements ListCellRenderer<ListPanelItem> {
        private static final Border SAFE_NO_FOCUS_BORDER = new EmptyBorder(1, 1, 1, 1);
        private static final Border DEFAULT_NO_FOCUS_BORDER = new EmptyBorder(1, 1, 1, 1);
        protected static Border noFocusBorder = DEFAULT_NO_FOCUS_BORDER;


        protected ListPanelItem() {
        }

        public ListPanelItem(HTMLContainer htmlContainer) {
            super(htmlContainer.getHtml());

            this.setInformation(htmlContainer.getInformation());
            this.setId(htmlContainer.getId());
            this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
            this.setBackground(Color.WHITE);
            this.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, new Color(10, 10, 10)));
            this.add(htmlContainer);
            this.setOpaque(true);

            final HTMLContainer param = htmlContainer;


        }

        public static ListPanelItem getCellRenderer() {
            return new ListPanelItem();
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends ListPanelItem> list, ListPanelItem value, int index, boolean isSelected, boolean cellHasFocus) {
            Email email = CommonUtil.GSON.fromJson(value.getInformation(), Email.class);
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
                if (email.getStatus().equals(Email.EmailStatus.UNREAD)) {
                    try {
                        new EmailController().changeEmailStatus(email, Email.EmailStatus.READED);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    value.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, new Color(10, 10, 10)));
                }
            } else {
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
            if (email.getStatus().equals(Email.EmailStatus.UNREAD)) {
                Border border = DefaultLookup.getBorder(this, ui, "List.focusCellHighlightBorder");
                value.setBorder(border);
            }

            value.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

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
