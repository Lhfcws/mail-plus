package edu.sysu.lhfcws.mailplus.client.ui.event.callback;

import edu.sysu.lhfcws.mailplus.commons.io.res.EmailResponse;
import edu.sysu.lhfcws.mailplus.commons.io.res.Response;
import edu.sysu.lhfcws.mailplus.commons.io.res.ResponseCallback;

import javax.swing.*;

/**
 * ResponseCallback of receiving new mails.
 * @author lhfcws
 * @time 14-12-29.
 */
public class NewMailResponseCallback implements ResponseCallback {
    @Override
    public void callback(Response res) {
        try {
            EmailResponse emailResponse = (EmailResponse) res;
            if (emailResponse.getEmails().size() > 0)
                JOptionPane.showMessageDialog(null,
                        "New emails received, please refresh your inbox.");
        } catch (Exception e) {}
    }
}
