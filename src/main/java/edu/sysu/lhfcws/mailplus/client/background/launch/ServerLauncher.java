package edu.sysu.lhfcws.mailplus.client.background.launch;

import edu.sysu.lhfcws.mailplus.client.background.client.MailPlusInternalClient;
import edu.sysu.lhfcws.mailplus.commons.io.Hub;
import edu.sysu.lhfcws.mailplus.commons.util.LogUtil;
import edu.sysu.lhfcws.mailplus.server.serv.MailPlusServer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author lhfcws
 * @time 14-11-15.
 */
public class ServerLauncher {
    private static Log LOG = LogFactory.getLog(ServerLauncher.class);

    public void launch() {
        MailPlusInternalClient.getInstance().start();
        MailPlusServer.getInstance().start();

        buildHub();
    }

    private void buildHub() {
        Hub hub = new Hub();
        hub.register(
                MailPlusInternalClient.getInstance().getClientHubEnd(),
                MailPlusServer.getInstance().getServerHubEnd());

        LogUtil.info(LOG, "Internal Server and Client Hub established.");
    }
}
