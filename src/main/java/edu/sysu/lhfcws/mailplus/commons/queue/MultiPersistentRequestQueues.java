package edu.sysu.lhfcws.mailplus.commons.queue;

import com.google.common.base.Preconditions;
import edu.sysu.lhfcws.mailplus.commons.io.req.Request;
import edu.sysu.lhfcws.mailplus.commons.queue.PersistentRequestQueue;
import edu.sysu.lhfcws.mailplus.server.util.CountDown;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Manage RQs and the corresponding CountDown.
 * @author lhfcws
 * @time 14-10-25.
 */
public class MultiPersistentRequestQueues {
    private static final int COUNTDOWN_NAP = 5;
    private ConcurrentHashMap<String, PersistentRequestQueue> pool;
    private ConcurrentHashMap<String, CountDown> countDown;

    MultiPersistentRequestQueues() {
        this.pool = new ConcurrentHashMap<String, PersistentRequestQueue>();
        this.countDown = new ConcurrentHashMap<String, CountDown>();
    }

    public void enQueue(String key, Request req) {
        Preconditions.checkArgument(key != null);
        Preconditions.checkArgument(req != null);
        Preconditions.checkArgument(req.getMailUser() != null);

        if (!this.pool.containsKey(key)) {
            this.pool.put(key, RQCenter.getRQ(key));
            this.countDown.put(key, new CountDown());
        }

        this.pool.get(key).enQueue(req);
    }

    public Request deQueue(String key) {
        Preconditions.checkArgument(key != null);
        Preconditions.checkArgument(this.pool.containsKey(key));

        Request req = this.pool.get(key).deQueue();
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
     * @param key
     */
    public void nap(String key) {
        this.countDown.get(key).set(COUNTDOWN_NAP);
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
