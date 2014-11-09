package edu.sysu.lhfcws.mailplus.server.serv.handler;

import com.google.gson.Gson;
import edu.sysu.lhfcws.mailplus.commons.base.Consts;
import edu.sysu.lhfcws.mailplus.commons.io.req.DeleteRequest;
import edu.sysu.lhfcws.mailplus.commons.io.req.ReceiveRequest;
import edu.sysu.lhfcws.mailplus.commons.io.req.Request;
import edu.sysu.lhfcws.mailplus.commons.io.req.SendRequest;
import edu.sysu.lhfcws.mailplus.commons.io.res.Response;
import edu.sysu.lhfcws.mailplus.commons.util.LogUtil;
import edu.sysu.lhfcws.mailplus.commons.util.PersistentRequestQueue;
import edu.sysu.lhfcws.mailplus.server.serv.POP3Server;
import edu.sysu.lhfcws.mailplus.server.serv.SMTPServer;

/**
 * Request handler.
 *
 * @author lhfcws
 * @time 14-10-23.
 */
public class RequestHandler {

    private static Gson gson = new Gson();
    private SMTPServer smtpServer;
    private POP3Server pop3Server;

    public RequestHandler(SMTPServer smtpServer, POP3Server pop3Server) {
        this.smtpServer = smtpServer;
        this.pop3Server = pop3Server;
    }

    public Response handleRequest(String json) {
        Request request = gson.fromJson(json, Request.class);
        LogUtil.debug(request.toString());
        Response res = new Response();
        res.setResID(request.getReqID());
        res.setAuthCode(request.getAuthCode());

        if (isRequestType(request, Request.RequestType.SEND))
            return handleSendRequest(gson.fromJson(json, SendRequest.class), res);
        else if (isRequestType(request, Request.RequestType.DELETE))
            return handleDeleteRequest(gson.fromJson(json, DeleteRequest.class), res);
        else if (isRequestType(request, Request.RequestType.RECEIVE))
            return handleReceiveRequest(gson.fromJson(json, ReceiveRequest.class), res);
        else {
            res.setStatus(Response.ResponseStatus.UNKNOWN_REQUEST);
            return res;
        }
    }

    private boolean isRequestType(Request req, Request.RequestType requestType) {
        return requestType.equals(req.getRequestType());
    }

    private Response handleSendRequest(SendRequest req, Response res) {
        PersistentRequestQueue.getRQ(Consts.SRQ).enQueue(req);
        res.setStatus(Response.ResponseStatus.WAITING);
        return res;
    }

    // TODO: implement handleReceiveRequest
    private Response handleReceiveRequest(ReceiveRequest req, Response res) {
        return res;
    }

    // TODO: implement handleDeleteRequest
    private Response handleDeleteRequest(DeleteRequest req, Response res) {
        return res;
    }
}
