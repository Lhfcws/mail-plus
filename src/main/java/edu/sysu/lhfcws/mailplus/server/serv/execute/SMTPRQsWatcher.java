package edu.sysu.lhfcws.mailplus.server.serv.execute;

import edu.sysu.lhfcws.mailplus.commons.io.req.SendRequest;
import edu.sysu.lhfcws.mailplus.commons.util.AdvRunnable;
import edu.sysu.lhfcws.mailplus.server.serv.SendServer;
import edu.sysu.lhfcws.mailplus.server.util.SMTPRequestQueues;

import java.util.List;

/**
 * @author lhfcws
 * @time 14-10-25.
 */
public class SMTPRQsWatcher extends AdvRunnable {

    public static final int WATCH_INTERVAL = 500;
    private SendServer sendServer;
    private SMTPRequestQueues smtpRequestQueues;

    public SMTPRQsWatcher(String name, SendServer sendServer) {
        super(name);
        this.sendServer = sendServer;
        this.smtpRequestQueues = new SMTPRequestQueues();
    }

    @Override
    public void run() {
        while (true) {
            this.smtpRequestQueues.countDown();
            List<String> availableSMTP = this.smtpRequestQueues.availableSMTP();

            for (String smtp : availableSMTP) {
                SendRequest req = this.smtpRequestQueues.deQueue(smtp);
                if (req == null)
                    continue;
                sendServer.send(req);
            }

            try {
                Thread.sleep(WATCH_INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
