package server.test.socket;

import edu.sysu.lhfcws.mailplus.commons.base.Consts;
import edu.sysu.lhfcws.mailplus.commons.io.CommonSocket;
import org.apache.commons.codec.binary.Base64;

import java.io.IOException;

/**
 * @author lhfcws
 * @time 14-10-24.
 */
public class RemoteConnectTest {

    private CommonSocket socket = new CommonSocket();
    private static int cnt = 0;

    public void start() throws IOException {
        String username = "lhfcws@163.com";
        String password = "lhfcws82283086";

        socket.connect("smtp.163.com", 25);
        String res;
        res = socket.receive();
        println(res);
        socket.send("HELO smtp.163.com" + Consts.CRLF);
        res = socket.receive();
        println(res);
        socket.send("AUTH LOGIN " + Consts.CRLF);
        socket.send(Base64.encodeBase64String(username.getBytes()) + Consts.CRLF);
        println(socket.receive());
        socket.send(Base64.encodeBase64String(password.getBytes()) + Consts.CRLF);
        println(socket.receive());
    }

    public void println(String msg) {
        cnt++;
        System.out.println(cnt + " " + msg);
    }

    public static void main(String[] args) throws IOException {
        new RemoteConnectTest().start();
    }
}
