package edu.sysu.lhfcws.mailplus.server.serv.handler;

import edu.sysu.lhfcws.mailplus.commons.io.req.DeleteRequest;
import edu.sysu.lhfcws.mailplus.commons.io.req.ReceiveRequest;
import edu.sysu.lhfcws.mailplus.commons.io.req.Request;
import edu.sysu.lhfcws.mailplus.commons.io.req.SendRequest;
import edu.sysu.lhfcws.mailplus.commons.io.res.Response;

/**
 * Request handler.
 * @author lhfcws
 * @time 14-10-23.
 */
public class RequestHandler {

    public RequestHandler() {}

    public Response handleRequest(Request request) {
        Response res = new Response();
        res.setResID(request.getReqID());
        res.setAuthCode(request.getAuthCode());

        if (isRequestType(request, Request.RequestType.SEND))
            return handleSendRequest((SendRequest) request, res);
        else if (isRequestType(request, Request.RequestType.DELETE))
            return handleDeleteRequest((DeleteRequest)request, res);
        else if (isRequestType(request, Request.RequestType.RECEIVE))
            return handleReceiveRequest((ReceiveRequest)request, res);
        else {
            res.setStatus(Response.ResponseStatus.UNKNOWN_REQUEST);
            return res;
        }
    }

    private boolean isRequestType(Request req, Request.RequestType requestType) {
        return requestType.equals(req.getRequestType());
    }

    private Response handleSendRequest(SendRequest req, Response res) {
//        RequestQueue.getRQ(Consts.SRQ).enQueue(req);
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
