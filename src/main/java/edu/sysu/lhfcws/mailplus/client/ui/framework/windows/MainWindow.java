package edu.sysu.lhfcws.mailplus.client.ui.framework.windows;

import javax.swing.*;

/**
 * Main window.
 * @author lhfcws
 * @time 14-10-28.
 */
public class MainWindow extends JFrame {

    private JMenuBar menuBar;
    private JScrollPane leftArea, listArea, contentArea;
    private JSplitPane splitPane;

    public MainWindow() {
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(150);
    }

    public void start() {
        initMenuBar();
        initLeftArea();
        initListArea();
        initContentArea();

        this.add(splitPane);
        this.pack();
        this.setVisible(true);
    }

    public void initMenuBar() {
        menuBar = new JMenuBar();

        this.setJMenuBar(menuBar);
    }

    public void initLeftArea() {
        leftArea = new JScrollPane();
        leftArea.createVerticalScrollBar();
        splitPane.add(leftArea);
    }

    public void initListArea() {
        splitPane.add(listArea);
    }

    public void initContentArea() {
        splitPane.add(contentArea);
    }
}
