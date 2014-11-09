package edu.sysu.lhfcws.mailplus.server.serv.executor;

import com.google.gson.Gson;
import edu.sysu.lhfcws.mailplus.commons.base.Consts;
import edu.sysu.lhfcws.mailplus.commons.io.InternalServerSocket;
import edu.sysu.lhfcws.mailplus.commons.io.req.Request;
import edu.sysu.lhfcws.mailplus.commons.io.res.Response;
import edu.sysu.lhfcws.mailplus.commons.util.AdvRunnable;
import edu.sysu.lhfcws.mailplus.commons.util.LogUtil;
import edu.sysu.lhfcws.mailplus.commons.util.MailplusConfig;
import edu.sysu.lhfcws.mailplus.server.serv.POP3Server;
import edu.sysu.lhfcws.mailplus.server.serv.SMTPServer;
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

    public ServerListener(String name, SMTPServer smtpServer, POP3Server pop3Server) {
        super(name);
        this.requestHandler = new RequestHandler(smtpServer, pop3Server);
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
            int port = MailplusConfig.getInstance().getInt(Consts.SERVER_PORT);
            internalServerSocket = new InternalServerSocket(port);

            // block here
            internalServerSocket.accept();
            LogUtil.debug("Server and client connection established. 0.0.0.0:" + port);

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
        Response res = requestHandler.handleRequest(msg);
        return res;
    }
}
