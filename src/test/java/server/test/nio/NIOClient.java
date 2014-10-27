package server.test.nio;

import edu.sysu.lhfcws.mailplus.commons.io.FileLineReader;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Set;

/**
 * @author lhfcws
 * @time 14-10-22.
 */
public class NIOClient {
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
        Selector selector = Selector.open();
// 创建一个套接字通道，注意这里必须使用无参形式
        SocketChannel channel = SocketChannel.open();
// 设置为非阻塞模式，这个方法必须在实际连接之前调用(所以open的时候不能提供服务器地址，否则会自动连接)
        channel.configureBlocking(false);
// 连接服务器，由于是非阻塞模式，这个方法会发起连接请求，并直接返回false(阻塞模式是一直等到链接成功并返回是否成功)
        channel.connect(new InetSocketAddress("0.0.0.0", 7777));
// 注册关联链接状态
        channel.register(selector, SelectionKey.OP_CONNECT);
        while (true) {
            if (selector.select() == 0)
                continue;
            // 获取发生了关注时间的Key集合，每个SelectionKey对应了注册的一个通道
            Set<SelectionKey> keys = selector.selectedKeys();
            for (SelectionKey key : keys) {
                // OP_CONNECT 两种情况，链接成功或失败这个方法都会返回true
                if (key.isConnectable()) {
                    // 由于非阻塞模式，connect只管发起连接请求，finishConnect()方法会阻塞到链接结束并返回是否成功
                    // 另外还有一个isConnectionPending()返回的是是否处于正在连接状态(还在三次握手中)
                    if (channel.finishConnect()) {
                        // 链接成功了可以做一些自己的处理，略
                        // ...
                        // 处理完后必须吧OP_CONNECT关注去掉，改为关注OP_READ
                        key.interestOps(SelectionKey.OP_WRITE);
                        System.out.println("[Finish connect]");
                    }
                }
                if (key.isWritable()) {
                    System.out.println("[begin to write]");
                    byte[] bytes = content.getBytes();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length);
                    byteBuffer.put(bytes);
                    byteBuffer.flip();

                    while (byteBuffer.hasRemaining()) {
                        int writeBytes = channel.write(byteBuffer);
                    }
                }
            }
        }
    }
}
