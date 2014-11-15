package edu.sysu.lhfcws.mailplus.client.background.executor;

import edu.sysu.lhfcws.mailplus.client.background.client.MailPlusInternalClient;
import edu.sysu.lhfcws.mailplus.client.ui.framework.window.MainWindow;
import edu.sysu.lhfcws.mailplus.commons.controller.EmailController;
import edu.sysu.lhfcws.mailplus.commons.db.sqlite.SQLite;
import edu.sysu.lhfcws.mailplus.commons.db.sqlite.sql.BaseDao;
import edu.sysu.lhfcws.mailplus.commons.io.req.ReceiveRequest;
import edu.sysu.lhfcws.mailplus.commons.io.res.EmailResponse;
import edu.sysu.lhfcws.mailplus.commons.io.res.Response;
import edu.sysu.lhfcws.mailplus.commons.io.res.ResponseCallback;
import edu.sysu.lhfcws.mailplus.commons.model.Email;
import edu.sysu.lhfcws.mailplus.commons.util.AdvRunnable;
import edu.sysu.lhfcws.mailplus.commons.util.LogUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.SQLException;

/**
 * Remind the RecvServer to receive new mails on a period.
 * @author lhfcws
 * @time 14-10-23.
 */
public class TimingRecvUpdater extends AdvRunnable {
    private static Log LOG = LogFactory.getLog(TimingRecvUpdater.class);
    public static final String NAME = "TimingRecvUpdater";
    public static final int UPDATE_INTERVAL = 10000;    // 10s

    private ReceiveRequest req;
    private MailPlusInternalClient client;

    public TimingRecvUpdater(MailPlusInternalClient client) {
        super(NAME);
        this.client = client;
    }

    public void initReceiveRequest() {
        this.req = new ReceiveRequest();
        this.req.setReceiveRequestType(ReceiveRequest.ReceiveRequestType.LATEST);
        this.req.generateAuthCode();
    }

    @Override
    public void run() {
        initReceiveRequest();

        while (true) {
            client.sendRequest(req, new ResponseCallback() {
                @Override
                public void callback(Response res) {
                    EmailController emailController = new EmailController();
                    EmailResponse emailResponse = (EmailResponse) res;
                    BaseDao dao = SQLite.getDao();


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

            try {
                Thread.sleep(UPDATE_INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
