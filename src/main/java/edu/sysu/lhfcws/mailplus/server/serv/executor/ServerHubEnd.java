package edu.sysu.lhfcws.mailplus.server.serv.executor;

import edu.sysu.lhfcws.mailplus.commons.io.HubEnd;
import edu.sysu.lhfcws.mailplus.commons.io.res.Response;
import edu.sysu.lhfcws.mailplus.commons.util.CommonUtil;
import edu.sysu.lhfcws.mailplus.commons.util.LogUtil;
import edu.sysu.lhfcws.mailplus.server.serv.handler.RequestHandler;

import java.io.IOException;

/**
 * Hub end in server.
 * @author lhfcws
 * @time 14-10-23.
 */
public class ServerHubEnd extends HubEnd {
    public static String NAME = "ServerHubEnd";
    private RequestHandler requestHandler;

    public ServerHubEnd() {
        super(NAME);
        this.requestHandler = new RequestHandler();
    }

    @Override
    public void onReceive(String msg) {
        Response response = requestHandler.handleRequest(msg);
        if (!Response.isAsync(response)) {
            this.sendResponse(response);
        }
    }

    /**
     * For async response callback
     * @param response
     * @throws IOException
     */
    public void sendResponse(Response response) {
        if (response != null) {
            String resMsg = CommonUtil.GSON.toJson(response);
            this.send(resMsg);
        }
    }
}
