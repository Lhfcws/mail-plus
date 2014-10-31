package edu.sysu.lhfcws.mailplus.server.serv.executor;

import edu.sysu.lhfcws.mailplus.commons.base.Consts;
import edu.sysu.lhfcws.mailplus.commons.io.req.Request;
import edu.sysu.lhfcws.mailplus.commons.io.req.SendRequest;
import edu.sysu.lhfcws.mailplus.commons.util.AdvRunnable;
import edu.sysu.lhfcws.mailplus.server.serv.SMTPServer;
import edu.sysu.lhfcws.mailplus.server.util.RequestQueue;

/**
 * SendRequestQueue Watcher.
 * @author lhfcws
 * @time 14-10-25.
 */
public class SRQWatcher extends AdvRunnable {

    public static final int WATCH_INTERVAL = 500;
    private RequestQueue srq;
    private SMTPServer SMTPServer;

    public SRQWatcher(String name, SMTPServer SMTPServer) {
        super(name);
        this.srq = RequestQueue.getRQ(Consts.SRQ);
        this.SMTPServer = SMTPServer;
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
            this.SMTPServer.dispatch(req);
        }
    }
}
