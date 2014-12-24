package edu.sysu.lhfcws.mailplus.commons.io;

import com.google.common.base.Preconditions;


/**
 * A mediator between client and server.
 * @author lhfcws
 * @time 14-11-14.
 */
public class Hub {

    private HubEnd end1;
    private HubEnd end2;

    public Hub() {
    }

    public boolean register(HubEnd end1, HubEnd end2) {
        if (end1.getId().equals(end2.getId()))
            return false;

        end1.setHub(this);
        end2.setHub(this);

        this.end1 = end1;
        this.end2 = end2;

        return true;
    }

    private HubEnd getHubEnd(String endID) {
        if (end1.getId().equals(endID))
            return end1;
        else  if (end2.getId().equals(endID))
            return end2;

        return null;
    }

    private HubEnd getAnotherHubEnd(String endID) {
        if (end1.getId().equals(endID))
            return end2;
        else  if (end2.getId().equals(endID))
            return end1;

        return null;
    }

    void sendFrom(String endID, String msg) {
        Preconditions.checkArgument(endID != null);

        this.getAnotherHubEnd(endID).onReceive(msg);
    }
}
