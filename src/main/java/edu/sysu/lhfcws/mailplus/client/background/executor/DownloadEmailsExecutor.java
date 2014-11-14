package edu.sysu.lhfcws.mailplus.client.background.executor;

import edu.sysu.lhfcws.mailplus.client.background.client.InternalClient;
import edu.sysu.lhfcws.mailplus.client.ui.framework.window.DownloadEmailsWindow;
import edu.sysu.lhfcws.mailplus.client.ui.framework.window.MainWindow;
import edu.sysu.lhfcws.mailplus.commons.controller.EmailController;
import edu.sysu.lhfcws.mailplus.commons.db.sqlite.SQLite;
import edu.sysu.lhfcws.mailplus.commons.db.sqlite.sql.BaseDao;
import edu.sysu.lhfcws.mailplus.commons.io.req.ReceiveRequest;
import edu.sysu.lhfcws.mailplus.commons.io.res.EmailResponse;
import edu.sysu.lhfcws.mailplus.commons.io.res.Response;
import edu.sysu.lhfcws.mailplus.commons.io.res.ResponseCallback;
import edu.sysu.lhfcws.mailplus.commons.model.Email;
import edu.sysu.lhfcws.mailplus.commons.model.MailUser;
import edu.sysu.lhfcws.mailplus.commons.util.AdvRunnable;
import edu.sysu.lhfcws.mailplus.commons.util.LogUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.SQLException;

/**
 * @author lhfcws
 * @time 14-11-6.
 */
public class DownloadEmailsExecutor extends AdvRunnable {
    private static Log LOG = LogFactory.getLog(DownloadEmailsExecutor.class);

    public static final String NAME = "downloadEmails";
    private MailUser mailUser;
    private InternalClient client;
    private DownloadEmailsWindow parent;

    public DownloadEmailsExecutor(
            MailUser mailUser, InternalClient client, DownloadEmailsWindow parent) {
        super(NAME);
        this.mailUser = mailUser;
        this.client = client;
        this.parent = parent;
    }

    @Override
    public void run() {
        final EmailController emailController = new EmailController();
        try {
            // Construct req
            int latestID = emailController.getLatestMailID(mailUser.getMailAddr());
            ReceiveRequest req = new ReceiveRequest();
            req.setMailID(latestID);
            req.setReceiveRequestType(ReceiveRequest.ReceiveRequestType.LATEST);
            req.setMailUser(mailUser);
            req.setMailID(2700);
            req.generateAuthCode();

            // Push req and callback to clientRQ
            parent.updateProgress(3);
            this.client.sendRequest(req, new ResponseCallback() {
                @Override
                public void callback(Response res) {
                    if (Response.isAsync(res))
                        return;
                    else if (res.getStatus().equals(Response.ResponseStatus.FAIL)) {
                        LogUtil.error(LOG, res.getMsg());
                        return;
                    }

                    EmailResponse emailResponse = (EmailResponse) res;
                    BaseDao dao = SQLite.getDao();

                    int size = emailResponse.getEmails().size();
                    int progress = 0;
                    parent.updateProgress(5);

                    for (Email email : emailResponse.getEmails()) {
                        try {
                            emailController.saveUnreadEmail(email);
                        } catch (SQLException e) {
                            LogUtil.error(LOG, e);
                        }
                        progress++;
                        double x = progress * 1.0 / size;
                        parent.updateProgress((int)Math.floor(x));
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    parent.updateProgress(100);
                }
            });

        } catch (SQLException e) {
            LogUtil.error(LOG, e);
        }
    }
}
