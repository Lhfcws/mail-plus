package edu.sysu.lhfcws.mailplus.commons.util;

import com.google.common.base.Preconditions;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Only for monitoring persistent thread. Do not register a non-persistent thread.
 * @author lhfcws
 * @time 14-10-25.
 */
public class ThreadMonitor {
    private static Log LOG = LogFactory.getLog(ThreadMonitor.class);
    private static final int DEFAULT_WATCH_INTERVAL = 1000;
    private ConcurrentHashMap<String, AdvRunnable> runnableMap;
    private ConcurrentHashMap<String, Thread> threadMap;
    private int watchInterval;

    public ThreadMonitor() {
        runnableMap = new ConcurrentHashMap<String, AdvRunnable>();
        threadMap = new ConcurrentHashMap<String, Thread>();
        this.watchInterval = DEFAULT_WATCH_INTERVAL;
    }

    public Thread getThread(String name) {
        Preconditions.checkArgument(name != null);

        return threadMap.get(name);
    }

    public Thread getThread(AdvRunnable advRunnable) {
        Preconditions.checkArgument(advRunnable != null);
        Preconditions.checkArgument(advRunnable.getName() != null);

        return threadMap.get(advRunnable.getName());
    }

    public AdvRunnable getAdvRunnable(String name) {
        Preconditions.checkArgument(name != null);

        return runnableMap.get(name);
    }

    public void register(AdvRunnable advRunnable, Thread thread) {
        Preconditions.checkArgument(advRunnable != null);
        Preconditions.checkArgument(thread != null);
        Preconditions.checkArgument(advRunnable.getName() != null);
        Preconditions.checkArgument(!this.runnableMap.containsKey(advRunnable.getName()));

        this.runnableMap.put(advRunnable.getName(), advRunnable);
        this.threadMap.put(advRunnable.getName(), thread);
    }

    public void register(AdvRunnable advRunnable) {
        Preconditions.checkArgument(advRunnable != null);
        Preconditions.checkArgument(advRunnable.getName() != null);
        Preconditions.checkArgument(!this.runnableMap.containsKey(advRunnable.getName()));

        this.runnableMap.put(advRunnable.getName(), advRunnable);
        this.threadMap.put(advRunnable.getName(), advRunnable.getNewThread());
    }

    public void unregister(AdvRunnable advRunnable) {
        Preconditions.checkArgument(advRunnable != null);
        Preconditions.checkArgument(advRunnable.getName() != null);

        getThread(advRunnable).stop();

        threadMap.remove(advRunnable.getName());
        runnableMap.remove(advRunnable.getName());
    }

    public void start() {
        for (Thread thread : threadMap.values()) {
            if (!thread.isAlive())
                thread.start();
        }
    }

    /**
     * Block method.
     */
    public void monitor() {
        while (true) {

            Set<Map.Entry<String, Thread>> set = threadMap.entrySet();
            for (Map.Entry<String, Thread> entry : set) {
                if (!entry.getValue().isAlive()) {
                    LOG.error(entry.getKey() + " is dead. Restarting it.");
                    entry.getValue().stop();
                    entry.getValue().start();
                }
            }

            try {
                Thread.sleep(watchInterval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public Thread asyncMonitor() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (true) {

                    Set<Map.Entry<String, Thread>> set = threadMap.entrySet();
                    for (Map.Entry<String, Thread> entry : set) {
                        if (!entry.getValue().isAlive()) {
                            LOG.error(entry.getKey() + " is dead. Restarting it.");
                            entry.getValue().stop();
                            entry.getValue().start();
                        }
                    }

                    try {
                        Thread.sleep(watchInterval);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
        return thread;
    }

    public int getWatchInterval() {
        return watchInterval;
    }

    public void setWatchInterval(int watchInterval) {
        this.watchInterval = watchInterval;
    }
}
