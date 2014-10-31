package edu.sysu.lhfcws.mailplus.server.serv;


import com.google.common.base.Preconditions;
import edu.sysu.lhfcws.mailplus.commons.util.CommonUtil;
import edu.sysu.lhfcws.mailplus.server.serv.executor.ServerListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * MailPlus main server.
 * @author lhfcws
 * @time 14-10-21.
 */
public class MailPlusServer {
    private static Log LOG = LogFactory.getLog(MailPlusServer.class);
    private Thread serverListenerThread;
    private ServerListener serverListener;
    private SMTPServer SMTPServer;

    private static MailPlusServer server = null;

    private MailPlusServer() {
    }

    public static MailPlusServer getInstance() {
        if (server == null) {
            synchronized (MailPlusServer.class) {
                if (server == null)
                    server = new MailPlusServer();
            }
        }
        return server;
    }

    public ServerListener getServerListener() {
        return serverListener;
    }

    /**
     * Start the smtpserver, including a client listener thread and a client listener thread monitor.
     */
    public void start() {
        this.startServerListener();
        this.startSendServer();

        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (!this.serverListenerThread.isAlive())
                startServerListener();
        }
    }

    /**
     * Start the listener thread, including restart.
     */
    public void startServerListener() {
        if (this.serverListenerThread != null) {
            LOG.info("Stopping dead server listener thread.");
            this.serverListenerThread.stop();
            LOG.info("Stopped dead server listener thread.");
            LOG.info("Restarting server listener thread.");
        }

        this.serverListener = new ServerListener(ServerListener.PREFIX + CommonUtil.getFormatNow());
        this.serverListenerThread = new Thread(this.serverListener, this.serverListener.getName());
        this.serverListenerThread.start();

        LOG.info("New ServerListener started.");
    }

    public void startSendServer() {
        Preconditions.checkArgument(this.serverListener != null);

        this.SMTPServer = new SMTPServer(this.serverListener);
        this.SMTPServer.start();

        LOG.info("New SendServer started.");
    }
}
