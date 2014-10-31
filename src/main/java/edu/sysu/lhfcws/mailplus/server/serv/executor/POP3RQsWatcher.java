package edu.sysu.lhfcws.mailplus.server.serv.executor;

import edu.sysu.lhfcws.mailplus.commons.io.req.Request;
import edu.sysu.lhfcws.mailplus.commons.util.AdvRunnable;
import edu.sysu.lhfcws.mailplus.server.serv.POP3Server;
import edu.sysu.lhfcws.mailplus.server.util.MultiRequestQueues;

import java.util.List;

/**
 * @author lhfcws
 * @time 14-10-28.
 */
public class POP3RQsWatcher extends AdvRunnable {

    public static final int WATCH_INTERVAL = 500;
    private POP3Server pop3Server;
    private MultiRequestQueues multiRequestQueues;

    public POP3RQsWatcher(String name, POP3Server pop3Server) {
        super(name);
        this.pop3Server = pop3Server;
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
            }

            try {
                Thread.sleep(WATCH_INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
