package edu.sysu.lhfcws.mailplus.server.serv.executor;

import edu.sysu.lhfcws.mailplus.commons.io.req.Request;
import edu.sysu.lhfcws.mailplus.commons.io.req.SendRequest;
import edu.sysu.lhfcws.mailplus.commons.util.AdvRunnable;
import edu.sysu.lhfcws.mailplus.server.serv.SMTPServer;
import edu.sysu.lhfcws.mailplus.server.util.MultiRequestQueues;

import java.util.List;

/**
 * @author lhfcws
 * @time 14-10-25.
 */
public class SMTPRQsWatcher extends AdvRunnable {

    public static final int WATCH_INTERVAL = 500;
    private SMTPServer SMTPServer;
    private MultiRequestQueues multiRequestQueues;

    public SMTPRQsWatcher(String name, SMTPServer SMTPServer) {
        super(name);
        this.SMTPServer = SMTPServer;
        this.multiRequestQueues = new MultiRequestQueues();
    }

    @Override
    public void run() {
        while (true) {
            this.multiRequestQueues.countDown();
            List<String> availableSMTP = this.multiRequestQueues.availableKeys();

            for (String smtp : availableSMTP) {
                Request req = this.multiRequestQueues.deQueue(smtp);
                if (req == null)
                    continue;
                SMTPServer.send((SendRequest) req);
            }

            try {
                Thread.sleep(WATCH_INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
