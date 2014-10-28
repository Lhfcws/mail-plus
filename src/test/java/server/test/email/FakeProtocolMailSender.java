package server.test.email;

import edu.sysu.lhfcws.mailplus.commons.io.req.SendRequest;
import edu.sysu.lhfcws.mailplus.commons.models.Email;
import edu.sysu.lhfcws.mailplus.commons.models.MailUser;
import edu.sysu.lhfcws.mailplus.server.protocols.SMTPClient;
import edu.sysu.lhfcws.mailplus.server.protocols.SMTPProtocolClient;
import edu.sysu.lhfcws.mailplus.server.serv.handler.RequestHandler;
import server.test.TestConsts;

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
        email.addCc("397923807@qq.com");
        email.setSubject("Testemail");
        email.setContent("This is a test");

        return email;
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

    public void start() throws Exception {
        Email email = initEmail();
        MailUser mailUser = initMailUser();

        SendRequest sendRequest = new SendRequest();
        sendRequest.setEmail(email);
        sendRequest.setMailUser(mailUser);

        System.out.println("Ready to send.");
        SMTPClient smtpClient = new SMTPProtocolClient(email, mailUser);
        System.out.println("Sending...");
        boolean ret = smtpClient.send();
        System.out.println("Email sended. Result : " + ret);
    }

    public static void main(String[] args) throws Exception {
        new FakeProtocolMailSender().start();
    }
}
