package client.preparation;

import edu.sysu.lhfcws.mailplus.client.background.client.MailPlusInternalClient;
import edu.sysu.lhfcws.mailplus.server.serv.MailPlusServer;

/**
 * @author lhfcws
 * @time 14-11-9.
 */
public class MailPlusStub {
    protected TestPreparation preparation;
    protected MailPlusInternalClient internalClient;
    protected MailPlusServer mailPlusServer;

    public void prepareTest() {
        this.preparation = new TestPreparation();
        this.internalClient = MailPlusInternalClient.getInstance();
        this.mailPlusServer = MailPlusServer.getInstance();
    }

    public MailPlusStub() {
        prepareTest();
    }

    public void start() {
        internalClient.start();
        mailPlusServer.start();
    }
}
