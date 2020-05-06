package cn.blackbulb.nio;

import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * 1.channel ：用于愿节点和目标节点的链接，在NIO中负责缓冲区中数据的传输。
 *
 *
 * 2.通道主要实现类
 * 1.FileChannel
 * 2.SocketChannel
 * 3.ServerSocketChannel
 * 4.DatagramChannel  UDP使用
 *
 *
 * 3.获取通道
 * 1.支持通道的类提供了getChannel方法
 * 本地IO:
 * FileInputStream/FileOutputStream
 * RandomAccessFile
 * 网络IO:
 * Socket
 * ServerSocket
 * DataGramSocket
 * 2.JDK 1.7中的方法针对各个通道提供了静态方法open
 * 3.JDK 1.7的Files工具类的newByteChannel
 * 4.通道之间的数据传输
 * transferFrom()
 * transferTo()
 *
 *
 * 5.分散Scatter与聚集Gather
 * 分散读取：将通道中的数据分散到过个缓冲区中
 * 聚集写入：将多个缓冲区中的数据聚集到通道中
 *
 * 6.自负集
 * 编码：字符串-》字节数组
 * 解码：字节数组-》字符串
 */
public class ChannelTest {

    /**
     * channel+非直接缓冲实现文件复制
     */
    @Test
    public void testFileChannel() {
        FileInputStream is = null;
        FileOutputStream os = null;
        FileChannel isChannel = null;
        FileChannel osChannel = null;
        try {
            is = new FileInputStream("/Users/bjqxdn0814/Desktop/模板文件配置详解.docx");
            os = new FileOutputStream("/Users/bjqxdn0814/Desktop/模板文件配置详解--复制复制.docx");
            isChannel = is.getChannel();
            osChannel = os.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            while (isChannel.read(byteBuffer) != -1) {
                byteBuffer.flip();
                osChannel.write(byteBuffer);
                byteBuffer.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                isChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                osChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * channel+直接缓冲区
     */
    @Test
    public void testFileChannel2() throws IOException {
        FileChannel ic = FileChannel.open(Paths.get("/Users/bjqxdn0814/Desktop/模板文件配置详解.docx"), StandardOpenOption.READ);
        FileChannel oc = FileChannel.open(
                Paths.get("/Users/bjqxdn0814/Desktop/模板文件配置详解1111.docx"),
                StandardOpenOption.CREATE,
                StandardOpenOption.READ,
                StandardOpenOption.WRITE);
        //直接内存，内存映射文件
        MappedByteBuffer inMappedBuf = ic.map(FileChannel.MapMode.READ_ONLY, 0, ic.size());
        MappedByteBuffer outMappedBuf = oc.map(FileChannel.MapMode.READ_WRITE, 0, ic.size());
        byte[] dst = new byte[inMappedBuf.limit()];
        inMappedBuf.get(dst);
        outMappedBuf.put(dst);
        ic.close();
        oc.close();
    }

    /**
     * 通道间直接传输数据
     */
    @Test
    public void testFileChannel3() throws IOException {
        FileChannel ic = FileChannel.open(Paths.get("/Users/bjqxdn0814/Desktop/模板文件配置详解.docx"), StandardOpenOption.READ);
        FileChannel oc = FileChannel.open(
                Paths.get("/Users/bjqxdn0814/Desktop/模板文件配置详解1111.docx"),
                StandardOpenOption.CREATE,
                StandardOpenOption.READ,
                StandardOpenOption.WRITE);
        ic.transferTo(0, ic.size(), oc);
        ic.close();
        oc.close();
    }

    /**
     * 分散聚集
     */
    @Test
    public void testFileChannel4() throws IOException {
        RandomAccessFile rw = new RandomAccessFile("/Users/bjqxdn0814/Desktop/删除oa模块.sql", "rw");
        FileChannel ic = rw.getChannel();

        ByteBuffer bb1 = ByteBuffer.allocate(100);
        ByteBuffer bb2 = ByteBuffer.allocate(1024);
        ByteBuffer bb3 = ByteBuffer.allocate(1024);
        ByteBuffer[] bbs = {bb1, bb2, bb3};
        //分散读取
        ic.read(bbs);
        for (ByteBuffer bb : bbs) {
            bb.flip();
            System.out.println(new String(bb.array(), 0, bb.limit()));
            System.out.println("============================================");
            System.out.println("============================================");
            System.out.println("============================================");
            System.out.println("============================================");
        }
        //聚集写入
        RandomAccessFile rww = new RandomAccessFile("/Users/bjqxdn0814/Desktop/删除oa模块111.sql", "rw");
        FileChannel channel = rww.getChannel();
        channel.write(bbs);
        channel.close();
        ic.close();
        //        oc.close();
    }

    /**
     * 编码解码
     */
    @Test
    public void test5() throws CharacterCodingException {
        Charset gbk = Charset.forName("GBK");
        CharsetEncoder charsetEncoder = gbk.newEncoder();
        CharsetDecoder charsetDecoder = gbk.newDecoder();
        CharBuffer allocate = CharBuffer.allocate(1024);
        allocate.put("王参牛逼啊");
        allocate.flip();
        ByteBuffer encode = charsetEncoder.encode(allocate);
        int limit = encode.limit();
        for (int i = 0; i < limit; i++) {
            System.out.println(encode.get());
        }
        encode.flip();
        CharBuffer decode = charsetDecoder.decode(encode);
        System.out.println(decode.toString());
        //支持CHarset直接编码解码
//        Charset utf = Charset.forName("UTF-8");
//        CharBuffer decode1 = utf.decode(encode);
//        ByteBuffer encode1 = utf.encode(encode);

    }


}
