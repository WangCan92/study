package cn.blackbulb.web.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author wangcan
 */
public class BIOServer {
    static byte[] bs = new byte[1024];

    public static void main(String[] args) {
        try (ServerSocket socket = new ServerSocket(8088)) {
            while (true) {
                System.out.println("wait conn………………………………");
                Socket clientSocket = socket.accept();
                System.out.println("conn success ..........");

                //多线程，防止读数据阻塞 弊端是多少个链接就会有多少个线程造成了线程浪费
                Thread thread = new Thread(() -> {
                    System.out.println("data........");
                    InputStream inputStream = null;
                    try {
                        inputStream = clientSocket.getInputStream();
                        System.out.println("data success........");
                        int len;
                        while ((len = inputStream.read(bs)) != -1) {
                            System.out.println(new String(bs, 0, len));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                });
                thread.start();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
