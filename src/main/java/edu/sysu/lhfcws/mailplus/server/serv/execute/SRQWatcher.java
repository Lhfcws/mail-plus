package edu.sysu.lhfcws.mailplus.server.serv.execute;

import edu.sysu.lhfcws.mailplus.commons.base.Consts;
import edu.sysu.lhfcws.mailplus.commons.io.req.Request;
import edu.sysu.lhfcws.mailplus.commons.io.req.SendRequest;
import edu.sysu.lhfcws.mailplus.commons.util.AdvRunnable;
import edu.sysu.lhfcws.mailplus.server.serv.SendServer;
import edu.sysu.lhfcws.mailplus.server.util.RequestQueue;

/**
 * SendRequestQueue Watcher.
 * @author lhfcws
 * @time 14-10-25.
 */
public class SRQWatcher extends AdvRunnable {

    public static final int WATCH_INTERVAL = 500;
    private RequestQueue srq;
    private SendServer sendServer;

    public SRQWatcher(String name, SendServer sendServer) {
        super(name);
        this.srq = RequestQueue.getRQ(Consts.SRQ);
        this.sendServer = sendServer;
    }

    @Override
    public void run() {
        while (true) {

            Request request = this.srq.deQueue();

            if (request == null) {
                try {
                    Thread.sleep(WATCH_INTERVAL);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            SendRequest req = (SendRequest) request;
            this.sendServer.dispatch(req);
        }
    }
}
