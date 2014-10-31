package client.test;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.spi.HttpServerProvider;

import java.io.*;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * @author lhfcws
 * @time 14-10-31.
 */
public class SimpleHttpServer {

    public static void main(String[] args) throws IOException {
        InetSocketAddress socketAddress = new InetSocketAddress("0.0.0.0", 52015);
        HttpServer server =
                HttpServerProvider.provider().createHttpServer(socketAddress, 10);
        server.setExecutor(Executors.newFixedThreadPool(10));
        server.createContext("/mailplus", new DefaultHttpHandler());
    }

    static class DefaultHttpHandler implements HttpHandler {
        public void handle(HttpExchange httpExchange) throws IOException {
            String responseMsg = "ok";   //响应信息
            InputStream in = httpExchange.getRequestBody(); //获得输入流
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String temp = null;
            while((temp = reader.readLine()) != null) {
                System.out.println("client request:"+temp);
            }
            httpExchange.sendResponseHeaders(200, responseMsg.getBytes().length); //设置响应头属性及响应信息的长度
            OutputStream out = httpExchange.getResponseBody();  //获得输出流
            out.write(responseMsg.getBytes());
            out.flush();
            httpExchange.close();
        }
    }
}
