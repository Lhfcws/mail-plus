package edu.sysu.lhfcws.mailplus.server.serv.executor;

import edu.sysu.lhfcws.mailplus.commons.base.Consts;
import edu.sysu.lhfcws.mailplus.commons.db.bdb.BDB;
import edu.sysu.lhfcws.mailplus.commons.io.req.SendRequest;
import edu.sysu.lhfcws.mailplus.commons.io.res.Response;
import edu.sysu.lhfcws.mailplus.commons.queue.PersistentRequestQueue;
import edu.sysu.lhfcws.mailplus.commons.queue.RQCenter;
import edu.sysu.lhfcws.mailplus.commons.util.AdvRunnable;
import edu.sysu.lhfcws.mailplus.commons.util.CommonUtil;
import edu.sysu.lhfcws.mailplus.commons.util.LogUtil;
import edu.sysu.lhfcws.mailplus.server.protocol.SMTPClient;
import edu.sysu.lhfcws.mailplus.server.protocol.SMTPProtocolClient;
import edu.sysu.lhfcws.mailplus.server.serv.SMTPServer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.net.SocketTimeoutException;

/**
 * The real executor that sends emails.
 * @author lhfcws
 * @time 14-10-24.
 */
public class SMTPExecutor extends AdvRunnable {

    private static Log LOG = LogFactory.getLog(SMTPExecutor.class);

    private SMTPServer SMTPServer;
    private SendRequest req;
    private static final String SMTP_WORKING_Q = "SMTPWorkingQ";

    public SMTPExecutor(String name, SMTPServer SMTPServer) {
        super(name);
        this.SMTPServer = SMTPServer;
    }

    public void init(SendRequest req) {
        this.req = req;
    }

    @Override
    public void run() {
        Response res = new Response();
        res.setAuthCode(req.getAuthCode());
        res.setResID(req.getReqID());

        try {
            SMTPClient smtpClient = new SMTPProtocolClient(req.getEmail(), req.getMailUser());
//            BDB bdb = BDB.getInstance(Consts.BDB_PATH + SMTP_WORKING_Q);

            System.out.println("Sending...");
//            bdb.set(name, CommonUtil.GSON.toJson(req));
            boolean ret = smtpClient.send();
            Response.ResponseStatus status = ret ?
                    Response.ResponseStatus.SUCCEED : Response.ResponseStatus.FAIL;
//            bdb.delete(name);
            System.out.println("Email sended.");
            res.setStatus(status);
        } catch (SocketTimeoutException e) {
            res.setStatus(Response.ResponseStatus.WAITING);
            res.setMsg(e.getMessage());
            SMTPServer.repushRequest(req);
        } catch (Exception e) {
            LogUtil.error(LOG, e);
            res.setMsg(e.getMessage());
            res.setStatus(Response.ResponseStatus.FAIL);
        } finally {
            SMTPServer.finish(req.getMailUser().getSmtpHost(), res);
        }
    }
}
