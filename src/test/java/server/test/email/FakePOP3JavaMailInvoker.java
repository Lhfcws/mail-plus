package server.test.email;

import edu.sysu.lhfcws.mailplus.commons.io.req.ReceiveRequest;
import edu.sysu.lhfcws.mailplus.commons.models.MailUser;
import edu.sysu.lhfcws.mailplus.server.protocols.POP3JavaMailClient;
import server.test.TestConsts;

/**
 * @author lhfcws
 * @time 14-10-28.
 */
public class FakePOP3JavaMailInvoker {

    public static void main(String[] args) throws Exception {
        new FakePOP3JavaMailInvoker().start();
    }

    private MailUser initMailUser() {
        MailUser mailUser = new MailUser();

        mailUser.setMailAddr(TestConsts.mail);
        mailUser.setPassword(TestConsts.password);
//        mailUser.setSmtpHost("0.0.0.0");
        mailUser.setSmtpHost("smtp.163.com");
        mailUser.setPop3Host("pop3.163.com");
        mailUser.setImapHost("imap.163.com");

        return mailUser;
    }

    public ReceiveRequest initReq() {
        ReceiveRequest req = new ReceiveRequest();

        req.setMailUser(initMailUser());

        return req;
    }

    public void start() throws Exception {

        POP3JavaMailClient client = new POP3JavaMailClient(initReq());
        client.receive("2689");
    }
}
