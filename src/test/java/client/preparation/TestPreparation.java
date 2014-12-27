package client.preparation;

import edu.sysu.lhfcws.mailplus.client.background.client.MailPlusInternalClient;
import edu.sysu.lhfcws.mailplus.client.background.launch.ServerLauncher;
import edu.sysu.lhfcws.mailplus.client.background.running.Token;
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
    public ServerLauncher serverLauncher;

    public TestPreparation() {
        serverLauncher = new ServerLauncher();
    }

    public void prepare() {
        serverLauncher.launch();

        LogUtil.info(LOG, "MailPlusServer and MailPlusInternalClient started.");
    }

    public Token prepareToken() {
        Token token = new Token();
        token.setEmail(TestConsts.mail);
        token.setPassword(TestConsts.password);
        token.setTimestamp(System.currentTimeMillis());
        return token;
    }

    public MailUser prepareMailUser() {
        MailUser mailUser = new MailUser();

        mailUser.setMailAddr(TestConsts.mail);
        mailUser.setPassword(TestConsts.password);
        mailUser.setImapHost("imap.126.com");
        mailUser.setSmtpHost("smtp.126.com");
        mailUser.setPop3Host("pop3.126.com");

        return mailUser;
    }
}
