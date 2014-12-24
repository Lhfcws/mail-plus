package edu.sysu.lhfcws.mailplus.server.serv.executor;

import edu.sysu.lhfcws.mailplus.commons.io.req.Request;
import edu.sysu.lhfcws.mailplus.commons.queue.RQCenter;
import edu.sysu.lhfcws.mailplus.commons.util.AdvRunnable;
import edu.sysu.lhfcws.mailplus.commons.util.LogUtil;
import edu.sysu.lhfcws.mailplus.server.serv.POP3Server;
import edu.sysu.lhfcws.mailplus.commons.queue.MultiPersistentRequestQueues;
import edu.sysu.lhfcws.mailplus.server.serv.handler.RequestHandler;

import java.util.List;

/**
 * Watcher for pop3 request.
 * @author lhfcws
 * @time 14-10-28.
 */
public class POP3RQsWatcher extends AdvRunnable {
    public static final String NAME = "POP3RQsWatcher";

    public static final int WATCH_INTERVAL = 500;
    private POP3Server pop3Server;
    private MultiPersistentRequestQueues multiPersistentRequestQueues;

    public POP3RQsWatcher(String name, POP3Server pop3Server) {
        super(name);
        this.pop3Server = pop3Server;
        this.multiPersistentRequestQueues = RQCenter.getMultiRQ(NAME);
    }

    @Override
    public void run() {
        while (true) {
            this.multiPersistentRequestQueues.countDown();
            List<String> availableKeys = this.multiPersistentRequestQueues.availableKeys();

            for (String key : availableKeys) {
                Request req = this.multiPersistentRequestQueues.deQueue(key);
//                LogUtil.debug("POP3RQsWatcher run:" + req);
                if (req == null)
                    continue;
                pop3Server.execute(req);
            }

            try {
                Thread.sleep(WATCH_INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
