package server.test.email;

import edu.sysu.lhfcws.mailplus.commons.io.req.SendRequest;
import edu.sysu.lhfcws.mailplus.commons.io.res.Response;
import edu.sysu.lhfcws.mailplus.commons.models.Email;
import edu.sysu.lhfcws.mailplus.commons.models.MailUser;
import edu.sysu.lhfcws.mailplus.server.protocols.SMTPProtocolSender;
import edu.sysu.lhfcws.mailplus.server.protocols.SMTPSender;
import edu.sysu.lhfcws.mailplus.server.serv.handler.RequestHandler;

/**
 * @author lhfcws
 * @time 14-10-24.
 */
public class FakeProtocolMailSender {

    private RequestHandler requestHandler = new RequestHandler();

    private Email initEmail() {
        Email email = new Email();

        email.setFrom("lhfcws@163.com");
        email.addTo("lhfcws@163.com");
        email.setTitle("Testemail");
        email.setContent("This is a test");

        return email;
    }

    private MailUser initMailUser() {
        MailUser mailUser = new MailUser();

        mailUser.setMailAddr("lhfcws@163.com");
        mailUser.setPassword("lhfcws82283086");
//        mailUser.setSmtpHost("0.0.0.0");
        mailUser.setSmtpHost("smtp.163.com");
        mailUser.setPop3Host("pop3.163.com");
        mailUser.setImapHost("imap.163.com");

        return mailUser;
    }

    public void start() throws Exception {
        Email email = initEmail();
        MailUser mailUser = initMailUser();

        SendRequest sendRequest = new SendRequest();
        sendRequest.setEmail(email);
        sendRequest.setMailUser(mailUser);

        System.out.println("Ready to send.");
        SMTPSender smtpSender = new SMTPProtocolSender(email, mailUser);
        System.out.println("Sending...");
        boolean ret = smtpSender.send();
        System.out.println("Email sended. Result : " + ret);
    }

    public static void main(String[] args) throws Exception {
        new FakeProtocolMailSender().start();
    }
}
