package client.preparation;

import edu.sysu.lhfcws.mailplus.client.background.client.MailPlusInternalClient;
import edu.sysu.lhfcws.mailplus.client.ui.framework.window.MainWindow;
import edu.sysu.lhfcws.mailplus.commons.model.MailUser;
import edu.sysu.lhfcws.mailplus.server.serv.MailPlusServer;

/**
 * @author lhfcws
 * @time 14-11-9.
 */
public class MailPlusStub {
    public TestPreparation preparation;
    public MailPlusInternalClient internalClient;
    public MailPlusServer mailPlusServer;

    public void prepareTest() {
        MainWindow.getInstance().addMailbox(preparation.prepareToken());
    }

    public MailPlusStub() {
        this.preparation = new TestPreparation();
        this.internalClient = MailPlusInternalClient.getInstance();
        this.mailPlusServer = MailPlusServer.getInstance();
    }

    public void start() {
        prepareTest();
    }
}
