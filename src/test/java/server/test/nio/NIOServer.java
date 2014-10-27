package server.test.nio;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Set;

/**
 * @author lhfcws
 * @time 14-10-22.
 */
public class NIOServer {

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
                Selector selector = Selector.open();
                ServerSocketChannel server = ServerSocketChannel.open();
                server.socket().bind(new InetSocketAddress("0.0.0.0", 7777));
                server.configureBlocking(false);
                server.register(selector, SelectionKey.OP_ACCEPT);
                while (true) {
                    if (selector.select() == 0)
                        continue;

                    Set<SelectionKey> keys = selector.selectedKeys();
                    for (SelectionKey key : keys) {
                        if (key.isAcceptable()) {
                            SocketChannel channel = ((ServerSocketChannel) key.channel()).accept();
                            channel.configureBlocking(false);
                            channel.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocate(2048));
                        }

                        if (key.isReadable()) {
                            SocketChannel channel = (SocketChannel) key.channel();
                            ByteBuffer buffer = (ByteBuffer) key.attachment();
                            int readBytes = 0;
                            StringBuilder sb = new StringBuilder();
                            byte[] bytes = new byte[2048];
                            byte b;

                            while (readBytes >= 0) {
                                readBytes = channel.read(buffer);
                                if (readBytes < 0)
                                    break;

                                b = buffer.get();
                                sb.append(b);
                                System.out.println("[read buffer] " + b);
                            }
                            System.out.println("[final recv] " + sb.toString());
                        }

                        keys.remove(key);
                    }
                }
            } catch (ClosedChannelException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
