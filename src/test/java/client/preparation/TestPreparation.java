package client.preparation;

import edu.sysu.lhfcws.mailplus.client.background.client.InternalClient;
import edu.sysu.lhfcws.mailplus.commons.model.MailUser;
import edu.sysu.lhfcws.mailplus.commons.util.LogUtil;
import edu.sysu.lhfcws.mailplus.server.serv.MailPlusServer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import server.test.TestConsts;

/**
 * @author lhfcws
 * @time 14-11-9.
 */
public class TestPreparation {
    public static Log LOG = LogFactory.getLog(TestPreparation.class);

    public TestPreparation() {

    }

    public void prepare() {
        MailPlusServer server = MailPlusServer.getInstance();
        server.start();

        InternalClient client = InternalClient.getInstance();
        client.start();

        LogUtil.info(LOG, "MailPlusServer and InternalClient started.");
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
