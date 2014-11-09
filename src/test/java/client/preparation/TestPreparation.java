package client.preparation;

import edu.sysu.lhfcws.mailplus.client.background.communication.InternalClient;
import edu.sysu.lhfcws.mailplus.commons.model.MailUser;
import edu.sysu.lhfcws.mailplus.server.serv.MailPlusServer;
import server.test.TestConsts;

/**
 * @author lhfcws
 * @time 14-11-9.
 */
public class TestPreparation {
    public TestPreparation() {

    }

    public void prepare() {
        MailPlusServer server = MailPlusServer.getInstance();
        server.start();

        InternalClient client = InternalClient.getInstance();
        client.start();
    }

    public MailUser prepareMailUser() {
        MailUser mailUser = new MailUser();

        mailUser.setMailAddr(TestConsts.mail);
        mailUser.setPassword(TestConsts.password);
        mailUser.setImapHost("imap.163.com");
        mailUser.setSmtpHost("smtp.163.com");
        mailUser.setPop3Host("pop3.163.com");

        return mailUser;
    }
}
