package edu.sysu.lhfcws.mailplus.commons.io;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * @author lhfcws
 * @time 14-10-22.
 */
public class InternalServerSocket extends InternalSocket {

    private static String CHARSET = "utf-8";
    private ServerSocket serverSocket;

    public InternalServerSocket(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void accept() throws IOException {
        this.socket = serverSocket.accept();
    }
}
