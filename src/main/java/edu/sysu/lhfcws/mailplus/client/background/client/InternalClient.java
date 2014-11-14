package edu.sysu.lhfcws.mailplus.client.background.client;

import edu.sysu.lhfcws.mailplus.client.background.executor.ClientHubEnd;
import edu.sysu.lhfcws.mailplus.client.background.executor.TimingRecvUpdater;
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
public class InternalClient {
    private static Log LOG = LogFactory.getLog(InternalClient.class);

    private ThreadMonitor threadMonitor;
    private ClientHubEnd clientHubEnd;

    private static InternalClient _client = null;

    public static InternalClient getInstance() {
        if (_client == null) {
            synchronized (InternalClient.class) {
                if (_client == null)
                    _client = new InternalClient();
            }
        }
        return _client;
    }

    private InternalClient() {
        this.clientHubEnd = new ClientHubEnd();
        this.threadMonitor = new ThreadMonitor();
        this.threadMonitor.register(new ClientRQWatcher(clientHubEnd));
        this.threadMonitor.register(new TimingRecvUpdater(this));
    }

    public void start() {
        threadMonitor.start();
        threadMonitor.asyncMonitor();
    }

    public void sendRequest(Request req, ResponseCallback callback) {
        clientHubEnd.pushRequest(req, callback);
    }


}
