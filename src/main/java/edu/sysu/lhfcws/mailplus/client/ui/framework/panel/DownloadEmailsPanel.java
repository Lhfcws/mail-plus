package edu.sysu.lhfcws.mailplus.client.ui.framework.panel;

import javax.swing.*;
import java.awt.*;

/**
 * @author lhfcws
 * @time 14-11-3.
 */
public class DownloadEmailsPanel extends JPanel {

    private JProgressBar progressBar;

    public DownloadEmailsPanel() {
        init();
    }

    private void init() {
        this.setBorder(BorderFactory.createEmptyBorder());
        this.setBackground(new Color(210, 210, 210));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        progressBar = new JProgressBar(JProgressBar.HORIZONTAL, 0, 100);
        progressBar.setStringPainted(true);

        this.add(new JLabel("Downloading emails from remote server, please wait ..."));
        this.add(new JLabel(" "));
        this.add(new JLabel(" "));
        this.add(progressBar);

        progressBar.setPreferredSize(new Dimension(270, 50));
    }

    public JProgressBar getProgressBar() {
        return progressBar;
    }
}
