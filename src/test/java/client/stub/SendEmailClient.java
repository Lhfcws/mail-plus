package client.stub;

import com.google.gson.Gson;
import edu.sysu.lhfcws.mailplus.commons.io.InternalSocket;
import edu.sysu.lhfcws.mailplus.commons.models.Email;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;

/**
 * @author lhfcws
 * @time 14-10-22.
 */
public class SendEmailClient {

    private static InternalSocket internalSocket;

    public static Email fakeEmail() {
        Email email = new Email();

        email.setFrom("lhfcws@163.com");
        email.addTo("397923807@qq.com");
        email.setTitle("Fake Email from MailPlus");
        email.setContent("Please ignore this email. This is a test email.");

        return email;
    }

    public static void sendEmailToSMTPServer(Email email) throws IOException {
        internalSocket.send((new Gson()).toJson(email));
    }

    public static void main(String[] args) throws Exception {
        int port = 52014;
        internalSocket = new InternalSocket();
        // block here
        internalSocket.connect("0.0.0.0", port);

        Thread thread = ClientListener.getThread("fake-client");
        thread.start();

        Email email = fakeEmail();
        sendEmailToSMTPServer(email);

        thread.join();
    }

    // ==================================
    protected static class ClientListener implements Runnable {
        public static String PREFIX = "SMTPServerClientThread-";
        private static Gson gson = new Gson();
        private static Log THREAD_LOG = LogFactory.getLog(ClientListener.class);
        private String name;

        private ClientListener(String name) {
            this.name = name;
        }

        public static Thread getThread(String name) {
            return new Thread(new ClientListener(name), name);
        }

        public String getName() {
            return this.name;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    String msg = internalSocket.receive();
                    System.out.println("[Server Response] " + msg);
                }

            } catch (IOException e) {
                e.printStackTrace();
                THREAD_LOG.error(e.getMessage(), e);
            }
        }

    }
}
