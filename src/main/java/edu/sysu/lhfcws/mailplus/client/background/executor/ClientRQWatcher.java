package edu.sysu.lhfcws.mailplus.client.background.executor;

import com.google.gson.Gson;
import edu.sysu.lhfcws.mailplus.client.background.communication.InternalClient;
import edu.sysu.lhfcws.mailplus.commons.base.Consts;
import edu.sysu.lhfcws.mailplus.commons.io.InternalSocket;
import edu.sysu.lhfcws.mailplus.commons.io.req.Request;
import edu.sysu.lhfcws.mailplus.commons.io.res.EmailResponse;
import edu.sysu.lhfcws.mailplus.commons.io.res.Response;
import edu.sysu.lhfcws.mailplus.commons.util.AdvRunnable;
import edu.sysu.lhfcws.mailplus.commons.util.LogUtil;
import edu.sysu.lhfcws.mailplus.commons.util.MailplusConfig;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;

/**
 * @author lhfcws
 * @time 14-11-3.
 */
public class ClientRQWatcher extends AdvRunnable {
    private static Log LOG = LogFactory.getLog(ClientRQWatcher.class);
    private static Gson gson = new Gson();
    public static final String NAME = "clientRQWatcher";
    private InternalSocket socket;
    private InternalClient client;

    public ClientRQWatcher(InternalClient client) {
        super(NAME);
        this.socket = new InternalSocket();
        this.client = client;
    }

    @Override
    public void run() {
        try {
            this.socket.connect("0.0.0.0", MailplusConfig.getInstance().getInt(Consts.SERVER_PORT));
        } catch (IOException e) {
            LogUtil.error(LOG, e);
        }

        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                LogUtil.error(LOG, e);
            }

            Request req = client.getClientRQ().deQueue();

            if (req == null)
                continue;

            try {
                this.socket.send(gson.toJson(req));
                String resMsg = this.socket.receive();

                Response response = Response.deserialize(resMsg);
                client.callback(response);
            } catch (IOException e) {
                LogUtil.error(LOG, e);
            }
        }
    }
}
