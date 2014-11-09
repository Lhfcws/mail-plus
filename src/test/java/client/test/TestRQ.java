package client.test;

import com.google.gson.Gson;
import edu.sysu.lhfcws.mailplus.commons.io.req.ReceiveRequest;
import edu.sysu.lhfcws.mailplus.commons.io.req.Request;
import edu.sysu.lhfcws.mailplus.commons.util.LogUtil;

/**
 * @author lhfcws
 * @time 14-11-9.
 */
public class TestRQ {
    public static void start() {
        Gson gson = new Gson();
        ReceiveRequest req = new ReceiveRequest();
        req.setReceiveRequestType(ReceiveRequest.ReceiveRequestType.LATEST);
        req.generateAuthCode();

        String json = gson.toJson(req);
        req = gson.fromJson(json, ReceiveRequest.class);
        Request req1 = gson.fromJson(json, Request.class);

        LogUtil.debug("" + req);
        LogUtil.debug("" + req1);
        req = (ReceiveRequest)req1;

//        PersistentRequestQueue RQ = InternalClient.getInstance().getClientRQ();
//        RQ.enQueue(req);
//        Request r = RQ.deQueue();
//        LogUtil.debug("" + r);
    }

    public static void main(String[] args) {
        start();
    }
}
