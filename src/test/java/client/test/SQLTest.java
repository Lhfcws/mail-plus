package client.test;

import edu.sysu.lhfcws.mailplus.commons.controller.EmailController;
import edu.sysu.lhfcws.mailplus.commons.model.Email;
import server.test.TestConsts;

import java.sql.SQLException;
import java.util.Date;

/**
 * @author lhfcws
 * @time 14-11-1.
 */
public class SQLTest {
    private EmailController emailController;

    public static void main(String[] args) {
        new SQLTest().start();
    }

    public void start() {
        emailControllerTest();
    }

    private Email initEmail() {
        Email email = new Email();

        email.setFrom(TestConsts.mail);
        email.addTo(TestConsts.mail);
        email.setSubject("Test SQL email object");
        email.setContent("Content");
        email.setSignature(TestConsts.mail);

        return email;
    }

    public void emailControllerTest() {
        emailController = new EmailController();
        Email email = initEmail();

        try {
            email = emailController.getEmailByID(446);
            System.out.println("[Email1] " + email.getSubject());
            email.setDate(new Date());
            emailController.saveDraft(email);
            System.out.println("[Email2]" + email.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
