package edu.sysu.lhfcws.mailplus.client.background.callback;

import edu.sysu.lhfcws.mailplus.client.ui.framework.window.MainWindow;
import edu.sysu.lhfcws.mailplus.commons.controller.EmailController;
import edu.sysu.lhfcws.mailplus.commons.io.res.EmailResponse;
import edu.sysu.lhfcws.mailplus.commons.io.res.Response;
import edu.sysu.lhfcws.mailplus.commons.io.res.ResponseCallback;
import edu.sysu.lhfcws.mailplus.commons.model.Email;

import java.sql.SQLException;
import java.util.List;

/**
 * @author lhfcws
 * @time 14-11-3.
 */
public class NewMailCallback implements ResponseCallback {
    @Override
    public void callback(Response res) {
        // Get emails from res
        EmailResponse emailResponse = (EmailResponse) res;
        List<Email> emails = emailResponse.getEmails();

        // Sync DB
        EmailController emailController = new EmailController();
        for (Email email : emails) {
            email.setStatus(Email.EmailStatus.UNREAD);
            try {
                emailController.saveUnreadEmail(email);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Refresh Mainwindow
        MainWindow.getInstance().refreshInbox();
    }
}
