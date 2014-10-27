package edu.sysu.lhfcws.mailplus.commons.util;

/**
 * Advanced wrapper for Runnable.
 * @author lhfcws
 * @time 14-10-23.
 */
public abstract class AdvRunnable implements Runnable {
    protected String name;

    protected AdvRunnable(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Thread getNewThread() {
        return new Thread(this, name);
    }
}
