package edu.sysu.lhfcws.mailplus.commons.io;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * @author lhfcws
 * @time 14-10-26.
 */
public class CommonServerSocket extends CommonSocket {

    private ServerSocket serverSocket;

    public CommonServerSocket(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
    }

    public void accept() throws IOException {
        this.socket = this.serverSocket.accept();
    }
}
