package cn.blackbulb.nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * @author wangcan
 */
public class NIOTest {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8088));
        socketChannel.configureBlocking(false);
        Scanner scanner = new Scanner(System.in);
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        while (scanner.hasNext()) {
            String s = scanner.nextLine();
            if ("quit".equals(s)) {
                System.out.println("断开链接。。。。。。。");
                break;
            }
            byteBuffer.put(s.getBytes());
            byteBuffer.flip();
            socketChannel.write(byteBuffer);
            byteBuffer.clear();
        }
        socketChannel.close();
    }

    @Test
    public void client() throws IOException {

    }

    @Test
    public void server() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(8088));
        serverSocketChannel.configureBlocking(false);
        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (selector.select()>0) {
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> it = selectionKeys.iterator();
            while (it.hasNext()) {
                SelectionKey sk = it.next();
                it.remove();
                if (sk.isAcceptable()) {
                    System.out.println("有一个链接啊，，，，，，");
                    ServerSocketChannel ssk = (ServerSocketChannel) sk.channel();
                    SocketChannel accept = ssk.accept();
                    accept.configureBlocking(false);
                    accept.register(selector, SelectionKey.OP_READ);
                } else if (sk.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel) sk.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    int len;
                    while ((len = socketChannel.read(byteBuffer)) > 0) {
                        byteBuffer.flip();
                        System.out.println("收到消息:" + new String(byteBuffer.array(), 0, len));
                        byteBuffer.clear();
                    }
                }
            }
        }
    }
}
