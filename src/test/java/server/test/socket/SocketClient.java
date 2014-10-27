package server.test.socket;

import edu.sysu.lhfcws.mailplus.commons.io.FileLineReader;

import java.io.DataOutputStream;
import java.net.Socket;
import java.net.URLEncoder;

/**
 * @author lhfcws
 * @time 14-10-22.
 */
public class SocketClient {
    public static String content;

    public static void main(String[] args) throws Exception {
        initContent();
        send();
    }

    public static void initContent() throws Exception {
        try {
            FileLineReader lineReader = new FileLineReader("/Users/lhfcws/coding/projects/mail-plus/src/test/resources/testio.txt");

            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = lineReader.readLine()) != null) {
                sb.append(line);
            }
            content = sb.toString();

            lineReader.close();
            System.out.println("[read txt finished] " + content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void send() throws Exception {
        Socket socket = new Socket("0.0.0.0", 52014);
        DataOutputStream os = new DataOutputStream(socket.getOutputStream());
        System.out.println("[begin to write]");
        String code = URLEncoder.encode(content, "utf-8");
        System.out.println(code);
        byte[] bytes = code.getBytes("utf-8");
        os.writeInt(bytes.length);
        os.write(bytes);
        os.flush();
        System.out.println("[Write finished]");
        os.close();
    }
}
