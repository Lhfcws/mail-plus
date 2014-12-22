package edu.sysu.lhfcws.mailplus.client.background.running;

import edu.sysu.lhfcws.mailplus.client.background.client.MailPlusInternalClient;
import edu.sysu.lhfcws.mailplus.client.ui.framework.window.MainWindow;
import edu.sysu.lhfcws.mailplus.commons.controller.EmailController;
import edu.sysu.lhfcws.mailplus.commons.db.sqlite.QueryUtil;
import edu.sysu.lhfcws.mailplus.commons.io.req.DeleteRequest;
import edu.sysu.lhfcws.mailplus.commons.io.req.ReceiveRequest;
import edu.sysu.lhfcws.mailplus.commons.io.req.RequestFactory;
import edu.sysu.lhfcws.mailplus.commons.io.req.SendRequest;
import edu.sysu.lhfcws.mailplus.commons.io.res.EmailResponse;
import edu.sysu.lhfcws.mailplus.commons.io.res.Response;
import edu.sysu.lhfcws.mailplus.commons.io.res.ResponseCallback;
import edu.sysu.lhfcws.mailplus.commons.model.Email;
import edu.sysu.lhfcws.mailplus.commons.util.LogUtil;
import edu.sysu.lhfcws.mailplus.commons.util.StringTruncator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.swing.*;
import java.sql.SQLException;

/**
 * Mail operation wrapper for internal clients.
 * @author lhfcws
 * @time 14-11-17.
 */
public class MailOperator {
    private static Log LOG = LogFactory.getLog(MailOperator.class);

    public void sendEmail(final Email email) {
        try {
            final EmailController emailController = new EmailController();
            emailController.saveSendEmail(email);
            SendRequest req = RequestFactory.createSendRequest(email);
            LogUtil.debug("send " + req.toString());
            MailPlusInternalClient.getInstance().sendRequest(req, new ResponseCallback() {
                @Override
                public void callback(Response res) {
                    email.setStatus(Email.EmailStatus.SENDED);
                    try {
                        emailController.changeEmailStatus(email, Email.EmailStatus.SENDED);
                    } catch (SQLException e) {
                        LogUtil.error(LOG, e);
                    }
                    JOptionPane.showMessageDialog(null, "Email sended.");
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void receiveLatest(Token token) {
        ReceiveRequest req = RequestFactory.createLatestReceiveRequest();
        MailPlusInternalClient.getInstance().sendRequest(req, new ResponseCallback() {
            @Override
            public void callback(Response res) {
                EmailController emailController = new EmailController();
                EmailResponse emailResponse = (EmailResponse) res;

                for (Email email : emailResponse.getEmails()) {
                    try {
                        emailController.saveUnreadEmail(email);
                    } catch (SQLException e) {
                        LogUtil.error(LOG, e);
                    }
                }

                if (emailResponse.getEmails().size() > 0)
                    MainWindow.getInstance().refreshInbox();
            }
        });
    }

    public void deleteEmail(final Email email) {

        if (email.getStatus().equals(Email.EmailStatus.UNREAD) ||
                email.getStatus().equals(Email.EmailStatus.READED)) {
            DeleteRequest req = RequestFactory.createDeleteRequest(email);
            MailPlusInternalClient.getInstance().sendRequest(req, new ResponseCallback() {
                @Override
                public void callback(Response res) {
                }
            });
        }

        try {
            new EmailController().deleteEmail(email);
        } catch (SQLException e) {
            LogUtil.error(LOG, e);
        }
    }
}
