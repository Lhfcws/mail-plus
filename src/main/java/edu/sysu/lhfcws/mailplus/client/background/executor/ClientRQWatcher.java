package edu.sysu.lhfcws.mailplus.client.background.executor;

import edu.sysu.lhfcws.mailplus.client.util.ClientConsts;
import edu.sysu.lhfcws.mailplus.commons.io.req.Request;
import edu.sysu.lhfcws.mailplus.commons.queue.PersistentRequestQueue;
import edu.sysu.lhfcws.mailplus.commons.queue.RQCenter;
import edu.sysu.lhfcws.mailplus.commons.util.AdvRunnable;
import edu.sysu.lhfcws.mailplus.commons.util.CommonUtil;
import edu.sysu.lhfcws.mailplus.commons.util.LogUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author lhfcws
 * @time 14-11-3.
 */
public class ClientRQWatcher extends AdvRunnable {
    private static Log LOG = LogFactory.getLog(ClientRQWatcher.class);
    public static final String NAME = "clientRQWatcher";
    private ClientHubEnd clientHubEnd;

    public ClientRQWatcher(ClientHubEnd clientHubEnd) {
        super(NAME);
        this.clientHubEnd = clientHubEnd;
    }

    @Override
    public void run() {
        PersistentRequestQueue clientRQ = RQCenter.getRQ(ClientConsts.CLIENT_REQ_Q);
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                LogUtil.error(LOG, e);
            }

            Request req = clientRQ.deQueue();
            if (req == null)
                continue;
            LogUtil.debug("client: " + req.toString());

            this.clientHubEnd.send(CommonUtil.GSON.toJson(req));
        }
    }
}
