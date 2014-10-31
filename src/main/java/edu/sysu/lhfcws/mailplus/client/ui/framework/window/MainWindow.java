package edu.sysu.lhfcws.mailplus.client.ui.framework.window;

import edu.sysu.lhfcws.mailplus.client.ui.framework.menu.MenuBar;
import edu.sysu.lhfcws.mailplus.client.ui.framework.panel.ContentPanel;
import edu.sysu.lhfcws.mailplus.client.ui.framework.panel.LeftPanel;
import edu.sysu.lhfcws.mailplus.client.ui.framework.panel.ListPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Main window.
 *
 * @author lhfcws
 * @time 14-10-28.
 */
public class MainWindow extends JFrame {

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
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        leftPanel = new LeftPanel();
        leftPanel.addMailbox("lhfcws@163.com");
        listPanel = new ListPanel();
        contentPanel = new ContentPanel();
    }

    public void start() {
        initMenuBar();

        JSplitPane internalSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        internalSplitPane.add(leftPanel);
        internalSplitPane.add(listPanel);
        internalSplitPane.setBorder(BorderFactory.createEmptyBorder());
        internalSplitPane.setEnabled(false);

        JEditorPane contentShower = new JEditorPane("text/html", "<h1>Oh yeah</h1>");

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

    public void initMenuBar() {
        this.setJMenuBar(new MenuBar());
    }

    private void setSize() {
        leftPanel.setSize(new Dimension(180, 700));
        listPanel.setSize(new Dimension(300, 700));
    }
}
