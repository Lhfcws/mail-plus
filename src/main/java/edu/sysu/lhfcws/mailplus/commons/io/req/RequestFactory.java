package edu.sysu.lhfcws.mailplus.commons.io.req;

import edu.sysu.lhfcws.mailplus.client.ui.framework.window.MainWindow;
import edu.sysu.lhfcws.mailplus.commons.controller.UserController;
import edu.sysu.lhfcws.mailplus.commons.model.Email;
import edu.sysu.lhfcws.mailplus.commons.util.LogUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.SQLException;

/**
 * @author lhfcws
 * @time 14-11-17.
 */
public class RequestFactory {
    private static Log LOG = LogFactory.getLog(RequestFactory.class);

    public static SendRequest createSendRequest(Email email) {
        SendRequest req = new SendRequest();
        req.setEmail(email);
        try {
            req.setMailUser(new UserController().getUser(email.getSignature()));
        } catch (SQLException e) {
            LogUtil.error(LOG, e);
        }
        req.generateAuthCode();
        return req;
    }

    public static DeleteRequest createDeleteRequest(Email email) {
        DeleteRequest req = new DeleteRequest();
        req.setMailID(email.getMailID());
        req.setDeleteRequestType(DeleteRequest.DeleteRequestType.DELETE_ONE);
        try {
            req.setMailUser(new UserController().getUser(email.getSignature()));
        } catch (SQLException e) {
            LogUtil.error(LOG, e);
        }
        req.generateAuthCode();
        return req;
    }

    public static ReceiveRequest createLatestReceiveRequest() {
        ReceiveRequest req = new ReceiveRequest();
        req.setReceiveRequestType(ReceiveRequest.ReceiveRequestType.LATEST);
        try {
            req.setMailUser(new UserController().getUser(
                    MainWindow.getInstance().getToken().getEmail()
            ));
        } catch (SQLException e) {
            LogUtil.error(LOG, e);
        }
        req.generateAuthCode();
        return req;
    }
}
