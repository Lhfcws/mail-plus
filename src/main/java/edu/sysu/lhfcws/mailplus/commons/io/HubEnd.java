package edu.sysu.lhfcws.mailplus.commons.io;

import edu.sysu.lhfcws.mailplus.commons.util.AdvRunnable;

/**
 * @author lhfcws
 * @time 14-11-14.
 */
public abstract class HubEnd extends AdvRunnable {

    protected String id;
    protected Hub hub;

    public HubEnd(String id) {
        super(id);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    void setHub(Hub hub) {
        this.hub = hub;
    }

    public void send(String msg) {
        hub.sendFrom(this.getId(), msg);
    }

    public abstract void onReceive(String msg);
}
