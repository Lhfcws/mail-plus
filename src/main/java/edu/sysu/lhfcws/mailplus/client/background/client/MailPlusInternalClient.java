package edu.sysu.lhfcws.mailplus.client.background.client;

import edu.sysu.lhfcws.mailplus.client.background.executor.ClientHubEnd;
import edu.sysu.lhfcws.mailplus.client.background.executor.TimingRecvUpdater;
import edu.sysu.lhfcws.mailplus.client.ui.framework.window.MainWindow;
import edu.sysu.lhfcws.mailplus.commons.io.res.ResponseCallback;
import edu.sysu.lhfcws.mailplus.client.background.executor.ClientRQWatcher;
import edu.sysu.lhfcws.mailplus.commons.io.req.Request;
import edu.sysu.lhfcws.mailplus.commons.util.ThreadMonitor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author lhfcws
 * @time 14-11-3.
 */
public class MailPlusInternalClient {
    private static Log LOG = LogFactory.getLog(MailPlusInternalClient.class);

    private ThreadMonitor threadMonitor;
    private ClientHubEnd clientHubEnd;

    private static MailPlusInternalClient _client = null;

    public static MailPlusInternalClient getInstance() {
        if (_client == null) {
            synchronized (MailPlusInternalClient.class) {
                if (_client == null)
                    _client = new MailPlusInternalClient();
            }
        }
        return _client;
    }

    private MailPlusInternalClient() {
        this.clientHubEnd = new ClientHubEnd();
        this.threadMonitor = new ThreadMonitor();
        this.threadMonitor.register(new ClientRQWatcher(clientHubEnd));
//        this.threadMonitor.register(new TimingRecvUpdater());
    }

    public void start() {
        threadMonitor.start();
        threadMonitor.asyncMonitor();
    }

    public void sendRequest(Request req, ResponseCallback callback) {
        clientHubEnd.pushRequest(req, callback);
    }

    public ClientHubEnd getClientHubEnd() {
        return clientHubEnd;
    }
}
