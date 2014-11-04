package client.test;

import edu.sysu.lhfcws.mailplus.commons.controller.EmailController;
import edu.sysu.lhfcws.mailplus.commons.model.Email;
import server.test.TestConsts;

import java.sql.SQLException;

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

        return email;
    }

    public void emailControllerTest() {
        emailController = new EmailController();
        Email email = initEmail();

        try {
            emailController.saveDraft(email);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
