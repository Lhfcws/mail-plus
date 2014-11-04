package edu.sysu.lhfcws.mailplus.client.background.executor;

import com.google.gson.Gson;
import edu.sysu.lhfcws.mailplus.client.background.communication.InternalClient;
import edu.sysu.lhfcws.mailplus.commons.base.Consts;
import edu.sysu.lhfcws.mailplus.commons.io.InternalSocket;
import edu.sysu.lhfcws.mailplus.commons.io.req.ReceiveRequest;
import edu.sysu.lhfcws.mailplus.commons.io.req.Request;
import edu.sysu.lhfcws.mailplus.commons.util.AdvRunnable;
import edu.sysu.lhfcws.mailplus.commons.util.MailplusConfig;

import java.io.IOException;

/**
 * TODO remove it to client package
 * Remind the RecvServer to receive new mails on a period.
 * @author lhfcws
 * @time 14-10-23.
 */
public class TimingRecvUpdater extends AdvRunnable {

    public static final String NAME = "TimingRecvUpdater";
    public static final int UPDATE_INTERVAL = 10000;    // 10s

    private ReceiveRequest req;
    private String reqMsg;
    private InternalClient client;

    public TimingRecvUpdater(InternalClient client) {
        super(NAME);
        this.client = client;
    }

    public void initReceiveRequest() {
        this.req = new ReceiveRequest();
        this.req.setReceiveRequestType(ReceiveRequest.ReceiveRequestType.LATEST);
        this.reqMsg = (new Gson()).toJson(this.req);
    }

    @Override
    public void run() {
        int port = Integer.valueOf(MailplusConfig.getInstance().get(Consts.SERVER_PORT));
        initReceiveRequest();

        while (true) {
            client.sendRequest(req, null);

            try {
                Thread.sleep(UPDATE_INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
