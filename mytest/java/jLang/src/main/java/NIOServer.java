import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;

public class NIOServer {
    private Selector selector;
    private ByteBuffer readBuffer = ByteBuffer.allocate(1024);
    private ByteBuffer sendBuffer = ByteBuffer.allocate(1024);
    String str;

    public void start() throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        ssc.bind(new InetSocketAddress("localhost", 8001));

        selector = Selector.open();
        ssc.register(selector, SelectionKey.OP_ACCEPT);
        while (!Thread.currentThread().isInterrupted()) {
            selector.select();
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = keys.iterator();
            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
                if (!key.isValid()) {
                    continue;
                }
                if (key.isAcceptable()) {
                    ServerSocketChannel ss = (ServerSocketChannel) key.channel();
                    SocketChannel clientChannel = ss.accept();
                    clientChannel.configureBlocking(false);
                    clientChannel.register(selector, SelectionKey.OP_READ);
                    System.out.println("客户端已经连接: " + clientChannel.getRemoteAddress());
                } else if (key.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    this.readBuffer.clear();
                    int numRead;
                    try {
                        numRead = socketChannel.read(this.readBuffer);
                    } catch (IOException e) {
                        key.cancel();
                        socketChannel.close();
                        return;
                    }
                    str = new String(readBuffer.array(), 0, numRead);
                    System.out.println(str);
                    socketChannel.register(selector, SelectionKey.OP_WRITE);
                } else if (key.isWritable()) {
                    SocketChannel channel = (SocketChannel) key.channel();
                    System.out.println("写入:" + str);

                    sendBuffer.clear();
                    sendBuffer.put(str.getBytes());
                    sendBuffer.flip();
                    channel.write(sendBuffer);
                    channel.register(selector, SelectionKey.OP_READ);
                }
                keyIterator.remove(); //该事件已经处理，可以丢弃
            }
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println("服务启动...");
        new NIOServer().start();
    }
}