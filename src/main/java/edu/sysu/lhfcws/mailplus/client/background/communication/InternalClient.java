package edu.sysu.lhfcws.mailplus.client.background.communication;

import edu.sysu.lhfcws.mailplus.commons.io.res.ResponseCallback;
import edu.sysu.lhfcws.mailplus.commons.io.res.ResponseID;
import edu.sysu.lhfcws.mailplus.client.background.executor.ClientRQWatcher;
import edu.sysu.lhfcws.mailplus.client.util.ClientConsts;
import edu.sysu.lhfcws.mailplus.commons.io.req.Request;
import edu.sysu.lhfcws.mailplus.commons.io.res.Response;
import edu.sysu.lhfcws.mailplus.commons.util.PersistentRequestQueue;
import edu.sysu.lhfcws.mailplus.commons.util.ThreadMonitor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author lhfcws
 * @time 14-11-3.
 */
public class InternalClient {
    private static Log LOG = LogFactory.getLog(InternalClient.class);
    private PersistentRequestQueue clientRQ;
    private HashMap<ResponseID, ResponseCallback> callbackPool;
    private ExecutorService threadPool;
    private ThreadMonitor threadMonitor;

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
        this.clientRQ = PersistentRequestQueue.getRQ(ClientConsts.CLIENT_REQ_Q);
        this.callbackPool = new HashMap<ResponseID, ResponseCallback>();
        this.threadPool = Executors.newCachedThreadPool();
        this.threadMonitor = new ThreadMonitor();
        this.threadMonitor.register(new ClientRQWatcher(this));
    }

    public void start() {
        threadMonitor.start();
        threadMonitor.monitor();
    }

    public PersistentRequestQueue getClientRQ() {
        return clientRQ;
    }

    public void sendRequest(Request req, ResponseCallback callback) {
        this.callbackPool.put(new ResponseID(req), callback);
        this.clientRQ.enQueue(req);
    }

    public void callback(final Response res) {
        this.threadPool.submit(new Runnable() {
            @Override
            public void run() {
                ResponseCallback responseCallback = callbackPool.get(new ResponseID(res));
                responseCallback.callback(res);
            }
        });
    }
}
