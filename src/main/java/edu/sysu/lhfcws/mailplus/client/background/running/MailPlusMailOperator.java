package edu.sysu.lhfcws.mailplus.client.background.running;

import edu.sysu.lhfcws.mailplus.client.background.client.MailPlusInternalClient;
import edu.sysu.lhfcws.mailplus.commons.controller.EmailController;
import edu.sysu.lhfcws.mailplus.commons.io.req.RequestFactory;
import edu.sysu.lhfcws.mailplus.commons.io.req.SendRequest;
import edu.sysu.lhfcws.mailplus.commons.io.res.Response;
import edu.sysu.lhfcws.mailplus.commons.io.res.ResponseCallback;
import edu.sysu.lhfcws.mailplus.commons.model.Email;
import edu.sysu.lhfcws.mailplus.commons.util.LogUtil;

import javax.swing.*;
import java.sql.SQLException;

/**
 * @author lhfcws
 * @time 14-11-17.
 */
public class MailPlusMailOperator {


    public void sendEmail(Email email) {
        try {
            new EmailController().saveSendEmail(email);
            SendRequest req = RequestFactory.createSendRequest(email);
            LogUtil.debug("send " + req.toString());
            MailPlusInternalClient.getInstance().sendRequest(req, new ResponseCallback() {
                @Override
                public void callback(Response res) {
                    JOptionPane.showMessageDialog(null, "Email sended.");
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
