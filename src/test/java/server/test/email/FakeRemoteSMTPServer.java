package server.test.email;

import edu.sysu.lhfcws.mailplus.commons.io.CommonServerSocket;

import java.io.IOException;

/**
 * @author lhfcws
 * @time 14-10-26.
 */
public class FakeRemoteSMTPServer {
    private CommonServerSocket serverSocket;


    public static void main(String[] args) throws IOException {
        new FakeRemoteSMTPServer().start();
    }

    public FakeRemoteSMTPServer() throws IOException {
        this.serverSocket = new CommonServerSocket(52001);
    }

    public void start() throws IOException {
        this.serverSocket.accept();
        System.out.println("Server Client connection established.");

        String testRes = "250 OK";
        while (true) {
            String msg = this.serverSocket.receive();
            System.out.print(msg);
            System.out.println(testRes);
            this.serverSocket.send(testRes);
        }
    }
}
