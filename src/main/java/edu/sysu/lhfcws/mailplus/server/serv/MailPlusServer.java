package edu.sysu.lhfcws.mailplus.server.serv;


import edu.sysu.lhfcws.mailplus.commons.util.LogUtil;
import edu.sysu.lhfcws.mailplus.commons.util.ThreadMonitor;
import edu.sysu.lhfcws.mailplus.server.serv.executor.ServerListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * MailPlus main server.
 *
 * @author lhfcws
 * @time 14-10-21.
 */
public class MailPlusServer {
    private static Log LOG = LogFactory.getLog(MailPlusServer.class);
    private ServerListener serverListener;
    private ThreadMonitor threadMonitor;

    private static MailPlusServer server = null;

    private MailPlusServer() {
        threadMonitor = new ThreadMonitor();
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

    /**
     * Start the smtpserver, including a client listener thread and a client listener thread monitor.
     */
    public void start() {
        this.serverListener = new ServerListener(ServerListener.PREFIX);
        threadMonitor.register(this.serverListener);
        threadMonitor.register(new SMTPServer(this.serverListener));
        threadMonitor.register(new POP3Server(this.serverListener));

        threadMonitor.start();
        threadMonitor.monitor();

        LogUtil.info(LOG, "New ServerListener, SMTPServer, POP3Server started.");
    }
}
