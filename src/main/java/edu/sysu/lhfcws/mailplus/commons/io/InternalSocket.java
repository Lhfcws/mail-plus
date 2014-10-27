package edu.sysu.lhfcws.mailplus.commons.io;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @author lhfcws
 * @time 14-10-22.
 */
public class InternalSocket extends CommonSocket {

    private DataInputStream is;
    private DataOutputStream os;

    public InternalSocket() {
    }

    /**
     *
     * Sending msg to socket binded.
     * @param msg
     * @throws IOException
     */
    @Override
    public void send(String msg) throws IOException {
        os = new DataOutputStream(socket.getOutputStream());

        byte[] bytes = URLEncoder.encode(msg, CHARSET).getBytes();
        int length = bytes.length;

        os.writeInt(length);
        os.write(bytes);
        os.flush();

    }

    /**
     * Return String if receive msg.
     * @return String receive message
     * @throws IOException
     */
    @Override
    public String receive() throws IOException {
        // unblock, process reading msg from socket
        is = new DataInputStream(this.socket.getInputStream());
        int length = is.readInt();
        byte[] bytes = new byte[length];

        is.readFully(bytes);

        String raw;
        raw = new String(bytes, CHARSET);
        raw = URLDecoder.decode(raw, CHARSET);

        return raw;
    }
}
