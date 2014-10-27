package edu.sysu.lhfcws.mailplus.commons.io;

import java.io.*;
import java.net.Socket;

/**
 * @author lhfcws
 * @time 14-10-24.
 */
public class CommonSocket {
    protected static String CHARSET = "utf-8";
    protected Socket socket;
    protected DataInputStream is;
    protected OutputStream os;

    public CommonSocket() {
    }

    public void connect(String host, int port) throws IOException {
        this.socket = new Socket(host, port);
    }

    public void close() throws IOException {
        this.socket.close();
    }

    /**
     * Sending msg to socket binded.
     *
     * @param msg
     * @throws IOException
     */
    public void send(String msg) throws IOException {
        byte[] bytes = msg.getBytes();
        os = this.socket.getOutputStream();

        os.write(bytes);
        os.flush();
    }

    /**
     * Return String if receive msg.
     *
     * @return String receive message
     * @throws IOException
     */
    public String receive() throws IOException {
        this.is = new DataInputStream(this.socket.getInputStream());
        StringBuilder sb = new StringBuilder();

        int ret = 0;
        while (ret == 0) {
            ret = is.available();
        }

        byte[] bytes = new byte[ret];

        is.readFully(bytes);

        String line = new String(bytes);

        return line;
    }
}
