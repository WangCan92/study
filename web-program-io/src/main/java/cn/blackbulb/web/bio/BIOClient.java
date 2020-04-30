package cn.blackbulb.web.bio;

import java.net.Socket;
import java.util.Scanner;

/**
 * @author wangcan
 */
public class BIOClient {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try (Socket socket = new Socket("127.0.0.1", 8088)) {
            while (scanner.hasNext()) {
                String l = scanner.nextLine();
                socket.getOutputStream().write(l.getBytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
