package edu.sysu.lhfcws.mailplus.server.serv.execute;

import edu.sysu.lhfcws.mailplus.commons.io.req.SendRequest;
import edu.sysu.lhfcws.mailplus.commons.io.res.Response;
import edu.sysu.lhfcws.mailplus.commons.util.AdvRunnable;
import edu.sysu.lhfcws.mailplus.server.protocols.SMTPProtocolSender;
import edu.sysu.lhfcws.mailplus.server.protocols.SMTPSender;
import edu.sysu.lhfcws.mailplus.server.serv.SendServer;

import java.net.SocketTimeoutException;

/**
 * The real executor that sends emails.
 * @author lhfcws
 * @time 14-10-24.
 */
public class SendExecutor extends AdvRunnable {

    private SendServer sendServer;
    private SendRequest req;

    public SendExecutor(String name, SendServer sendServer) {
        super(name);
        this.sendServer = sendServer;
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
            SMTPSender smtpSender = new SMTPProtocolSender(req.getEmail(), req.getMailUser());

            System.out.println("Sending...");
            boolean ret = smtpSender.send();
            Response.ResponseStatus status = ret ?
                    Response.ResponseStatus.SUCCEED : Response.ResponseStatus.FAIL;
            System.out.println("Email sended.");
            res.setStatus(status);
        } catch (SocketTimeoutException e) {
            res.setStatus(Response.ResponseStatus.WAITING);
            sendServer.repushRequest(req);
        } catch (Exception e) {
            System.out.println("Email send failed.");
            e.printStackTrace();
            res.setStatus(Response.ResponseStatus.FAIL);
        } finally {
            sendServer.finishSend(req.getMailUser().getSmtpHost(), res);
        }
    }
}
