package edu.sysu.lhfcws.mailplus.client.ui.event.http;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.spi.HttpServerProvider;
import edu.sysu.lhfcws.mailplus.client.ui.event.http.handler.NewEmailHandler;

import java.io.*;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * Local httpserver.
 * @author lhfcws
 * @time 14-10-31.
 */
public class LocalHttpServer {
    public static final String HTTP_ROOT = "/maiplus";
    private HttpServer server;

    public void startServer() {
        InetSocketAddress socketAddress = new InetSocketAddress("0.0.0.0", 52015);
        server = null;

        try {
            server = HttpServerProvider.provider().createHttpServer(socketAddress, 10);
        } catch (IOException e) {
            e.printStackTrace();
        }
        server.setExecutor(Executors.newFixedThreadPool(10));

        createRoutes();

        server.start();
    }

    public void stop() {
        server.stop(0);
    }

    /**
     * Route url
     */
    public void createRoutes() {
        route("/new_email", new NewEmailHandler());
    }

    private void route(String path, HttpHandler httpHandler) {
        HttpContext context = server.createContext(String.format("%s%s", HTTP_ROOT, path), httpHandler);
        context.getFilters().add(new ParameterFilter());
    }

//    static class DefaultHttpHandler implements HttpHandler {
//        @SuppressWarnings("unchecked")
//        public void handle(HttpExchange httpExchange) throws IOException {
//            Map<String, Object> params =
//                    (Map<String, Object>) httpExchange.getAttribute(ParameterFilter.NAME);
//
//
//        }
//    }
}
