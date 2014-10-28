package edu.sysu.lhfcws.mailplus.server.util;

import com.google.common.base.Preconditions;
import edu.sysu.lhfcws.mailplus.commons.io.req.Request;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Manage RQs and the corresponding CountDown.
 * @author lhfcws
 * @time 14-10-25.
 */
public class MultiRequestQueues {
    private static final int COUNTDOWN_NAP = 5;
    private ConcurrentHashMap<String, RequestQueue> pool;
    private ConcurrentHashMap<String, CountDown> countDown;

    public MultiRequestQueues() {
        this.pool = new ConcurrentHashMap<String, RequestQueue>();
        this.countDown = new ConcurrentHashMap<String, CountDown>();
    }

    public void enQueue(Request req) {
        Preconditions.checkArgument(req != null);
        Preconditions.checkArgument(req.getMailUser() != null);

        String smtp = req.getMailUser().getSmtpHost();
        if (!this.pool.containsKey(smtp)) {
            this.pool.put(smtp, RequestQueue.getRQ(smtp));
        }

        this.pool.get(smtp).enQueue(req);
    }

    public Request deQueue(String smtp) {
        Preconditions.checkArgument(smtp != null);
        Preconditions.checkArgument(this.pool.containsKey(smtp));

        Request req = this.pool.get(smtp).deQueue();
        if (req == null)
            return null;
        return req;
    }

    /**
     * Countdown all the smtpHosts to gradually release some smtpHosts from nap.
     */
    public void countDown() {
        for (CountDown counter : this.countDown.values()) {
            counter.countDown();
        }
    }

    /**
     * If a smtp server is socket timeout,
     * then we'll make the smtpHost in nap and wait a while to retry.
     * @param smtp
     */
    public void nap(String smtp) {
        this.countDown.get(smtp).set(COUNTDOWN_NAP);
    }

    /**
     * Get the smtpHosts those are not in nap.
     * @return
     */
    public List<String> availableKeys() {
        List<String> list = new LinkedList<String>();

        for (Map.Entry<String, CountDown> entry : this.countDown.entrySet()) {
            if (entry.getValue().available())
                list.add(entry.getKey());
        }

        return list;
    }
}
