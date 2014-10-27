package server.test.socket;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;

/**
 * @author lhfcws
 * @time 14-10-22.
 */
public class SocketServer {

    private static Thread server;

    public static void main(String[] args) throws InterruptedException {
        server = NIOServerRunnable.getThread("test");
        server.start();
        System.out.println("Started server.");
        server.join();
    }

    protected static class NIOServerRunnable implements Runnable {
        public static String PREFIX = "NIOTestServer-";
        private static Log THREAD_LOG = LogFactory.getLog(NIOServerRunnable.class);
        private String name;

        private NIOServerRunnable(String name) {
            this.name = name;
        }

        public static Thread getThread(String name) {
            return new Thread(new NIOServerRunnable(name), name);
        }

        public String getName() {
            return this.name;
        }

        @Override
        public void run() {
            try {
                int port = 52014;
                ServerSocket serverSocket = new ServerSocket(port);
                Socket socket = serverSocket.accept();
                DataInputStream inputStream = new DataInputStream(socket.getInputStream());

                int length = inputStream.readInt();
                byte[] bytes = new byte[length];

                inputStream.readFully(bytes);

                String raw;
                raw = new String(bytes, "utf-8");
                raw = URLDecoder.decode(raw, "utf-8");

                System.out.println("[final answer] " + raw);

                if (inputStream != null)
                    inputStream.close();
                if (socket != null)
                    socket.close();
                if (serverSocket != null)
                    serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
