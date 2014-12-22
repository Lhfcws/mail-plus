package edu.sysu.lhfcws.mailplus.client.background.executor;

import edu.sysu.lhfcws.mailplus.client.util.ClientConsts;
import edu.sysu.lhfcws.mailplus.commons.io.HubEnd;
import edu.sysu.lhfcws.mailplus.commons.io.req.Request;
import edu.sysu.lhfcws.mailplus.commons.io.res.Response;
import edu.sysu.lhfcws.mailplus.commons.io.res.ResponseCallback;
import edu.sysu.lhfcws.mailplus.commons.io.res.ResponseID;
import edu.sysu.lhfcws.mailplus.commons.queue.PersistentRequestQueue;
import edu.sysu.lhfcws.mailplus.commons.queue.RQCenter;
import edu.sysu.lhfcws.mailplus.commons.util.LogUtil;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author lhfcws
 * @time 14-11-14.
 */
public class ClientHubEnd extends HubEnd {
    public static final String NAME = "ClientHubEnd";

    private HashMap<ResponseID, ResponseCallback> callbackPool;
    private ExecutorService threadPool;
    private PersistentRequestQueue clientRQ;

    public ClientHubEnd() {
        super(NAME);
        this.callbackPool = new HashMap<ResponseID, ResponseCallback>();
        this.threadPool = Executors.newCachedThreadPool();
        this.clientRQ = RQCenter.getRQ(ClientConsts.CLIENT_REQ_Q);
    }

    @Override
    public void onReceive(String msg) {
        Response response = Response.deserialize(msg);
        callback(response);
    }

    public void pushRequest(Request req, ResponseCallback callback) {
        LogUtil.debug("hub send: " + callback);
        this.callbackPool.put(new ResponseID(req), callback);
        this.clientRQ.enQueue(req);
    }

    public void callback(final Response res) {
        this.threadPool.submit(new Runnable() {
            @Override
            public void run() {
                ResponseID responseID = new ResponseID(res);
                ResponseCallback responseCallback = callbackPool.get(responseID);
                if (responseCallback == null)
                    return;

                responseCallback.callback(res);
                if (!Response.isAsync(res))
                    callbackPool.remove(responseID);
            }
        });
    }
}
