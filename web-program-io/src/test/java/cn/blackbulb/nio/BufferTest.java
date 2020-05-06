package cn.blackbulb.nio;

import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * @author wangcan
 * 1.缓冲区负责数据的存取
 * 根据不同的数据类型不同提供了相应类型的缓冲类型，boolean除外
 *      ByteBuffer
 *      CharBuffer
 *      ShortBuffer
 *      IntBuffer
 *      LongBuffer
 *      FloatBuffer
 *      DoubleBuffer
 * 2.方法
 *      get():获取缓冲区中的数据
 *      put():存入数据到缓冲区
 *      hasRemaining:缓冲区中是否还有可操作数据
 *      remaining：缓冲区中可操作数据的数量
 *  3.缓冲区中的核心属性：
 *      capacity：容量，一旦生命不能改变
 *      limit:界限，表示缓冲区中可以操作数据的大小。limit后数据不能进行读写
 *      position:位置，表示缓冲区中正在操作数据的位置 position<=limit<=capacity
 *      mark:可以记录当前position，可以通过reset（）回复position的位置到mark
 *  4.直接缓冲区与非直接缓冲区
 *      非直接缓冲区：通过allocate方法分配缓冲区，将缓冲区建立在jvm的内存中
 *      直接缓冲区：通过allocateDirect方法分配直接缓冲区，将缓冲区建立在物理内存中，可以提高效率 0 copy
 */
public class BufferTest {
    @Test
    public void bufferTest01() {
        //分配缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());
        System.out.println("===================put后=====================");
        buf.put("abc".getBytes());
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());
        buf.flip();
        System.out.println("===================flip后=====================");
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());
        byte[] outBytes = new byte[buf.limit()];
        buf.get(outBytes);
        System.out.println(new String(outBytes));
        System.out.println("===================get后=====================");
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());
        System.out.println("===================reWind后=====================");
        //rewind后可以重复读
        buf.rewind();
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());
        buf.clear();
        System.out.println("===================clear后=====================");
        //clear后数据还存在，但是处于被遗忘状态，不可读
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

    }
}
