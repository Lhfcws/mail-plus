package edu.sysu.lhfcws.mailplus.server.serv.executor;

import edu.sysu.lhfcws.mailplus.commons.io.req.Request;
import edu.sysu.lhfcws.mailplus.commons.io.req.SendRequest;
import edu.sysu.lhfcws.mailplus.commons.queue.RQCenter;
import edu.sysu.lhfcws.mailplus.commons.util.AdvRunnable;
import edu.sysu.lhfcws.mailplus.server.serv.SMTPServer;
import edu.sysu.lhfcws.mailplus.commons.queue.MultiPersistentRequestQueues;

import java.util.List;

/**
 * Watcher for SMTP request.
 * @author lhfcws
 * @time 14-10-25.
 */
public class SMTPRQsWatcher extends AdvRunnable {

    public static final String NAME = "SMTPRQsWatcher";

    public static final int WATCH_INTERVAL = 500;
    private SMTPServer SMTPServer;
    private MultiPersistentRequestQueues multiPersistentRequestQueues;

    public SMTPRQsWatcher(String name, SMTPServer SMTPServer) {
        super(name);
        this.SMTPServer = SMTPServer;
        this.multiPersistentRequestQueues = RQCenter.getMultiRQ(NAME);
    }

    @Override
    public void run() {
        while (true) {
            this.multiPersistentRequestQueues.countDown();
            List<String> availableSMTP = this.multiPersistentRequestQueues.availableKeys();

            for (String smtp : availableSMTP) {
                Request req = this.multiPersistentRequestQueues.deQueue(smtp);
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
