package me.test.jdk.java.nio;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

/**
 * 测试 Buffer
 * 0 <= mark <= position <= limit <= capacity
 * <p>
 * limit 仅仅在读取模式中有意义,代表可读取范围的最大边界。
 * 在写入模式时, 默认均为 capacity。
 * <p>
 * CharBuffer#toString()   仅仅在读取模式时才有意义,返回值为未读取的数据。
 * <p>
 * 默认/clear/compact 进入待写入模式
 * flip               进入待读取模式
 */
public class BufferTest {

    public static void main(String[] args) throws IOException, NoSuchFieldException, IllegalAccessException {

        // 默认: 待写入状态
        CharBuffer buf = CharBuffer.allocate(10);
        print(" 1", buf);

        buf.put("abc");
        print(" 2", buf);

        buf.put("123");
        print(" 3", buf);

        // 只有转换了之后，才能读取内容
        buf.flip();
        print(" 4", buf);

        // 假设在这里读取了4个字符
        System.out.println(buf.get());
        System.out.println(buf.get());
        System.out.println(buf.get());
        System.out.println(buf.get());
        print(" 5", buf);

        buf.mark();
        print(" 6", buf);

        System.out.println(buf.get());
        System.out.println(buf.get());
        print(" 7", buf);

        buf.reset();
        print(" 8", buf);

        // 将残留的未读取的数据放到 开头, 进入待写入状态。
        buf.compact();
        print(" 9", buf);

        buf.put("xyz");
        print("10", buf);


        // 清空缓存, 进入待写入模式。
        buf.clear();
        print("10", buf);

    }


    static void print(String tag, CharBuffer buf) throws NoSuchFieldException, IllegalAccessException {

        Field markField = Buffer.class.getDeclaredField("mark");
        markField.setAccessible(true);
        int m = (int) markField.get(buf);

        // 获取内部字符数组的所有内容
        int oldLimt = buf.limit();
        buf.limit(buf.capacity());
        char[] b = new char[buf.capacity()];
        for (int i = 0; i < buf.capacity(); i++) {
            b[i] = buf.get(i);
        }
        buf.limit(oldLimt);

        char[] content = new char[buf.limit() - buf.position()];
        for (int i = 0; i < content.length; i++) {
            content[i] = buf.get(buf.position() + i);
        }


        System.out.printf("[%s] : mark/position/limit/capacity : (remaining) = %2d/%2d/%2d/%2d : (%2d) " +
                        ": %s, toString()=%10s, content=%10s%n",
                tag,
                m,
                buf.position(),
                buf.limit(),
                buf.capacity(),
                buf.remaining(),
                String.valueOf(b),
                buf.toString(),
                String.valueOf(content));
    }
}
