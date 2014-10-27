package edu.sysu.lhfcws.mailplus.server.serv.execute;

import com.google.gson.Gson;
import edu.sysu.lhfcws.mailplus.commons.base.Consts;
import edu.sysu.lhfcws.mailplus.commons.io.InternalServerSocket;
import edu.sysu.lhfcws.mailplus.commons.io.req.Request;
import edu.sysu.lhfcws.mailplus.commons.io.res.Response;
import edu.sysu.lhfcws.mailplus.commons.util.AdvRunnable;
import edu.sysu.lhfcws.mailplus.commons.util.MailplusConfig;
import edu.sysu.lhfcws.mailplus.server.serv.handler.RequestHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;

/**
 * @author lhfcws
 * @time 14-10-23.
 */
public class ServerListener extends AdvRunnable {
    public static String PREFIX = "ServerListener-";
    private static Gson gson = new Gson();
    private static Log THREAD_LOG = LogFactory.getLog(ServerListener.class);
    private InternalServerSocket internalServerSocket;
    private RequestHandler requestHandler;

    public ServerListener(String name) {
        super(name);
        this.requestHandler = new RequestHandler();
    }

    public static Thread getThread(String name) {
        return new Thread(new ServerListener(name), name);
    }

    public InternalServerSocket getServerSocket() {
        return internalServerSocket;
    }

    @Override
    /**
     * A async response will be returned by invoke sendResponse() method.
     * This sync method only return sync Response.
     */
    public void run() {
        try {
            int port = Integer.valueOf(MailplusConfig.getInstance().get(Consts.SERVER_PORT));
            internalServerSocket = new InternalServerSocket(port);

            // block here
            internalServerSocket.accept();

            while (true) {
                String msg = internalServerSocket.receive();

                Response response = process(msg);
                if (!Response.isAsync(response)) {
                    String resMsg = gson.toJson(response);
                    internalServerSocket.send(resMsg);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            THREAD_LOG.error(e.getMessage(), e);
        }
    }

    /**
     * For async response callback
     * @param response
     * @throws IOException
     */
    public void sendResponse(Response response) throws IOException {
        if (response != null) {
            String resMsg = gson.toJson(response);
            internalServerSocket.send(resMsg);
        }
    }

    /**
     * @param msg
     * @return
     */
    private Response process(String msg) {
        Request req = gson.fromJson(msg, Request.class);
        Response res = requestHandler.handleRequest(req);
        return res;
    }
}
