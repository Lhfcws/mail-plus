package edu.sysu.lhfcws.mailplus.client.ui.framework.window;

import com.google.common.base.Preconditions;
import edu.sysu.lhfcws.mailplus.client.background.client.MailPlusInternalClient;
import edu.sysu.lhfcws.mailplus.client.background.executor.DownloadEmailsExecutor;
import edu.sysu.lhfcws.mailplus.client.ui.event.callback.WindowLaunchCallback;
import edu.sysu.lhfcws.mailplus.client.ui.framework.panel.DownloadEmailsPanel;
import edu.sysu.lhfcws.mailplus.commons.model.MailUser;

import java.awt.*;


/**
 * The window that shows the progress of downloading emails.
 * @author lhfcws
 * @time 14-11-3.
 */
public class DownloadEmailsWindow extends AbstractWindow {

    private DownloadEmailsPanel downloadEmailsPanel;
    private MailUser mailUser = null;

    private static DownloadEmailsWindow _window = null;

    public static DownloadEmailsWindow getInstance() {
        if (_window == null) {
            synchronized (DownloadEmailsWindow.class) {
                if (_window == null) {
                    _window = new DownloadEmailsWindow();
                }
            }
        }

        return _window;
    }

    public static boolean isClosed() {
        return _window == null;
    }

    private DownloadEmailsWindow() {
        super("Email synchronizing...");
    }

    public void setMailUser(MailUser mailUser) {
        this.mailUser = mailUser;
    }

    protected void init() {
        downloadEmailsPanel = new DownloadEmailsPanel();
        this.add(downloadEmailsPanel);
    }

    public void start(MailUser mailUser) {
        this.setMailUser(mailUser);
        this.start();
    }

    @Override
    public void start() {
        if (mailUser != null) {
            this.pack();
            this.setVisible(true);
            this.setPreferredSize(new Dimension(400, 200));
            this.setLocation(600, 300);

            this.startDownload();
        }
    }

    @Override
    public void close() {
        if (_window != null)
            _window.dispose();
        _window = null;
    }

    /**
     * Start downloading.
     */
    public void startDownload() {
        DownloadEmailsExecutor executor =
                new DownloadEmailsExecutor(mailUser, MailPlusInternalClient.getInstance(), this);
        executor.getNewThread().start();
        this.updateProgress(1);
    }

    /**
     * 0 <= x <= 100
     *
     * @param x
     */
    public void updateProgress(int x) {
        Preconditions.checkArgument(x >= 0);
        Preconditions.checkArgument(x <= 100);

        this.downloadEmailsPanel.getProgressBar().setValue(x);

        if (x == 100) {
            new WindowLaunchCallback().callback(null);
            this.close();
        }
    }
}
