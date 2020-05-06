package cn.blackbulb.web.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * @author wangcan
 */
public class NIOSelectorServer {
    public static void main(String[] args) {
        try {
            ServerSocketChannel ssc = ServerSocketChannel.open();
            ssc.socket().bind(new InetSocketAddress(8088));
            ssc.configureBlocking(false);
            Selector selector = Selector.open();
            ssc.register(selector,SelectionKey.OP_ACCEPT);
            System.out.println(selector.keys().size());
            ByteBuffer readBuff = ByteBuffer.allocate(1024);
            ByteBuffer wirteBuff = ByteBuffer.allocate(128);
            wirteBuff.put("received".getBytes());
            wirteBuff.flip();

            while (true){
                int nReady = selector.select();
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> it = keys.iterator();
                while (it.hasNext()){
                    SelectionKey key = it.next();
                    it.remove();
                    if (key.isAcceptable()) {
                        System.out.println("is acceptable");
                        ServerSocketChannel server = (ServerSocketChannel)key.channel();
                        SocketChannel accept = server.accept();
                        if(accept==null)
                            continue;
                        accept.configureBlocking(false);
                        accept.register(selector,SelectionKey.OP_READ);
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        buffer.put("hi,new channel".getBytes());
                        buffer.flip();
                        accept.write(buffer);

                    }
                    if(key.isReadable()){
                        SocketChannel socketChannel = (SocketChannel)key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        buffer.clear();

                        socketChannel.read(buffer);
                        if (buffer.hasRemaining()) {
                            String receiveData= Charset.forName("UTF-8").decode(buffer).toString();
                            System.out.println("receiveData:"+receiveData);
                        }
                        buffer.flip();


                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
