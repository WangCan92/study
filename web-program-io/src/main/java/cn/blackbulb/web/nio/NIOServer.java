package cn.blackbulb.web.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author wangcan
 */
public class NIOServer {
    private static List<SocketChannel> channelList = new ArrayList<>();
    private static ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

    public static void main(String[] args) {
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            InetSocketAddress inetSocketAddress = new InetSocketAddress(8088);
            serverSocketChannel.bind(inetSocketAddress);
            serverSocketChannel.configureBlocking(false);
            while (true) {
                Iterator<SocketChannel> iterator = channelList.iterator();
                //大部分list中是没有写入的所以浪费cpu
                while (iterator.hasNext()){
                    SocketChannel socketChannel = iterator.next();
                    int len;
                    while ((len = socketChannel.read(byteBuffer)) > 0) {
                        byteBuffer.flip();
                        byte[] bs = new byte[len];
                        byteBuffer.get(bs);
                        System.out.println(new String(bs));
                        byteBuffer.flip();
                    }
                    if(len==-1){
                        System.out.println("断开链接。。。。。。");
                        iterator.remove();
                    }
                }
                SocketChannel accept = serverSocketChannel.accept();
                if (accept==null) {
//                    System.out.println("无人链接。。。。。。");
                }else{
                    System.out.println("conn success........");
                    accept.configureBlocking(false);
                    channelList.add(accept);
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
