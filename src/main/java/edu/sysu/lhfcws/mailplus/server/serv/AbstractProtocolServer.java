package edu.sysu.lhfcws.mailplus.server.serv;

import edu.sysu.lhfcws.mailplus.commons.io.req.Request;
import edu.sysu.lhfcws.mailplus.commons.io.res.Response;

/**
 * @author lhfcws
 * @time 14-12-29.
 */
public interface AbstractProtocolServer {
    public void finish(String host, Response res);
    public void dispatch(Request req);
    public void repushRequest(Request req);
    public void start();
}
