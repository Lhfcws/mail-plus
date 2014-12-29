package edu.sysu.lhfcws.mailplus.server.serv.executor;

import edu.sysu.lhfcws.mailplus.commons.base.Consts;
import edu.sysu.lhfcws.mailplus.commons.db.bdb.BDB;
import edu.sysu.lhfcws.mailplus.commons.io.req.DeleteRequest;
import edu.sysu.lhfcws.mailplus.commons.io.req.ReceiveRequest;
import edu.sysu.lhfcws.mailplus.commons.io.req.Request;
import edu.sysu.lhfcws.mailplus.commons.io.res.EmailResponse;
import edu.sysu.lhfcws.mailplus.commons.io.res.Response;
import edu.sysu.lhfcws.mailplus.commons.model.Email;
import edu.sysu.lhfcws.mailplus.commons.util.AdvRunnable;
import edu.sysu.lhfcws.mailplus.commons.util.CommonUtil;
import edu.sysu.lhfcws.mailplus.commons.util.LogUtil;
import edu.sysu.lhfcws.mailplus.server.protocol.POP3Client;
import edu.sysu.lhfcws.mailplus.server.protocol.POP3JavaMailClient;
import edu.sysu.lhfcws.mailplus.server.protocol.POP3ProtocolClient;
import edu.sysu.lhfcws.mailplus.server.serv.POP3Server;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.net.SocketTimeoutException;
import java.util.List;

/**
 * The real executor that connects to POP3 server.
 *
 * @author lhfcws
 * @time 14-10-27.
 */
public class POP3Executor extends AdvRunnable {

    private static Log LOG = LogFactory.getLog(POP3Executor.class);

    private Request req;
    private POP3Server pop3Server;
    private static final String POP3_WORKING_Q = "POP3WorkingQ";

    public POP3Executor(String name, POP3Server pop3Server) {
        super(name);
        this.pop3Server = pop3Server;
    }

    public void init(Request req) {
        this.req = req;
    }

    @Override
    public void run() {
        POP3Client pop3Client = new POP3JavaMailClient(req);
        Response res = new Response();
        res.setResID(req.getReqID());
        res.setAuthCode(req.getAuthCode());
        res.setStatus(Response.ResponseStatus.SUCCEED);

//        LogUtil.debug("POP3Executor run: " + req);

        try {
//            BDB bdb = BDB.getInstance(Consts.BDB_PATH + POP3_WORKING_Q);
//            bdb.set(name, CommonUtil.GSON.toJson(req));

            if (req.getRequestType().equals(Request.RequestType.DELETE)) {
                DeleteRequest deleteRequest = (DeleteRequest) req;
                pop3Client.delete(deleteRequest.getMailID());
                pop3Server.finish(req.getMailUser().getPop3Host(), res);
            } else if (req.getRequestType().equals(Request.RequestType.RECEIVE)) {
                ReceiveRequest receiveRequest = (ReceiveRequest) req;
                EmailResponse emailResponse = new EmailResponse();
                emailResponse.setResID(req.getReqID());
                emailResponse.setAuthCode(req.getAuthCode());
                emailResponse.setStatus(Response.ResponseStatus.SUCCEED);

                if (receiveRequest.getReceiveRequestType()
                        .equals(ReceiveRequest.ReceiveRequestType.LATEST)) {
                    List<Email> list = pop3Client.receiveLatest(receiveRequest.getMailID());
                    emailResponse.setEmails(list);
                } else if (receiveRequest.getReceiveRequestType()
                        .equals(ReceiveRequest.ReceiveRequestType.MAIL)) {
                    Email email = pop3Client.receive(receiveRequest.getMailID());
                    emailResponse.addEmail(email);
                }

//                bdb.delete(name);
                pop3Server.finish(req.getMailUser().getPop3Host(), emailResponse);
            }
        } catch (SocketTimeoutException e) {
            res.setStatus(Response.ResponseStatus.WAITING);
            res.setMsg(e.getMessage());
            pop3Server.repushRequest(req);
        } catch (Exception e) {
            LogUtil.error(LOG, e);
            res.setStatus(Response.ResponseStatus.FAIL);
            res.setMsg(e.getMessage());
            pop3Server.finish(req.getMailUser().getPop3Host(), res);
        }
    }
}
