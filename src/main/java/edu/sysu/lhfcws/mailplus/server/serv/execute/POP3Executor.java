package edu.sysu.lhfcws.mailplus.server.serv.execute;

import edu.sysu.lhfcws.mailplus.commons.io.req.DeleteRequest;
import edu.sysu.lhfcws.mailplus.commons.io.req.ReceiveRequest;
import edu.sysu.lhfcws.mailplus.commons.io.req.Request;
import edu.sysu.lhfcws.mailplus.commons.io.res.Response;
import edu.sysu.lhfcws.mailplus.commons.models.Email;
import edu.sysu.lhfcws.mailplus.commons.util.AdvRunnable;
import edu.sysu.lhfcws.mailplus.commons.util.LogUtil;
import edu.sysu.lhfcws.mailplus.server.protocols.POP3Client;
import edu.sysu.lhfcws.mailplus.server.protocols.POP3ProtocolClient;
import edu.sysu.lhfcws.mailplus.server.serv.POP3Server;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.net.SocketTimeoutException;
import java.util.List;

/**
 *  The real executor that connects to POP3 server.
 * @author lhfcws
 * @time 14-10-27.
 */
public class POP3Executor extends AdvRunnable {

    private static Log LOG = LogFactory.getLog(POP3Executor.class);

    private Request req;
    private POP3Server pop3Server;

    public POP3Executor(String name, POP3Server pop3Server) {
        super(name);
        this.pop3Server = pop3Server;
    }

    public void init(Request req) {
        this.req = req;
    }

    @Override
    public void run() {
        POP3Client pop3Client = new POP3ProtocolClient(req);
        Response res = new Response();
        res.setResID(req.getReqID());
        res.setAuthCode(req.getAuthCode());
        res.setStatus(Response.ResponseStatus.SUCCEED);

        try {
            if (req.getRequestType().equals(Request.RequestType.DELETE)) {
                DeleteRequest deleteRequest = (DeleteRequest) req;
                pop3Client.delete(deleteRequest.getMailID());
            } else if (req.getRequestType().equals(Request.RequestType.RECEIVE)) {
                ReceiveRequest receiveRequest = (ReceiveRequest) req;
                if (receiveRequest.getReceiveRequestType()
                        .equals(ReceiveRequest.ReceiveRequestType.LATEST)) {
                    List<Email> list = pop3Client.receiveLatest(receiveRequest.getMailID());
                } else if (receiveRequest.getReceiveRequestType()
                        .equals(ReceiveRequest.ReceiveRequestType.MAIL)) {
                    Email email = pop3Client.receive(receiveRequest.getMailID());
                }
            }
        } catch (SocketTimeoutException e) {
            res.setStatus(Response.ResponseStatus.WAITING);
            res.setMsg(e.getMessage());

        } catch (Exception e) {
            LogUtil.error(LOG, e);
            res.setStatus(Response.ResponseStatus.FAIL);
            res.setMsg(e.getMessage());
        } finally {
            pop3Server.finish(req.getMailUser().getPop3Host(), res);
        }
    }
}
