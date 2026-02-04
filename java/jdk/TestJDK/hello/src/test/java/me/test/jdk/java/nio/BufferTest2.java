package me.test.jdk.java.nio;

import java.nio.CharBuffer;

/**
 *
 */
public class BufferTest2 {

    static int count = 0;

    private String recv() {
        switch (++count) {
            case 1:
                return "123\n45\n6789";
            case 2:
                return "abc";
            case 3:
                return "de\n";
        }
        return "\n";
    }

    /**
     * 要求写出 read 函数
     * - 要调用 recv 函数作为数据源。
     * - 每次调用返回一行记录。
     * <p>
     * 比如针对上述 recv 方法，打印结果应该是
     * - 123
     * - 45
     * - 6789abcde
     */
    public static void main(String[] args) throws Exception {
        BufferTest2 r = new BufferTest2();
        System.out.println(r.read());
        System.out.println(r.read());
        System.out.println(r.read());
    }

    // ---------
    final int LINE_MAX_LENGTH = 2014;
    StringBuilder msgBuilder = new StringBuilder(LINE_MAX_LENGTH);
    CharBuffer buf = CharBuffer.allocate(128);

    private String read() {
        msgBuilder.setLength(0);
        while (true) {
            // 写入模式
            buf.put(recv());
            if (readUntilEnd(buf, msgBuilder)) {
                break;
            }
        }

        String line = msgBuilder.toString();
        msgBuilder.setLength(0);
        return line;
    }

    /**
     * 是否 buf 读完了。
     *
     * @return true - 读完了，需要再读取新的数据
     */
    private boolean readUntilEnd(CharBuffer buf, StringBuilder b) {
        buf.flip();
        while (buf.hasRemaining()) {
            char c = buf.get();
            if (buf.length() >= LINE_MAX_LENGTH) {
                throw new RuntimeException("超长LINE,不支持");
            }
            b.append(c);

            if (c == '\n') {

                // 还没读完，已经读到换行符，可以输出了。
                buf.compact();
                return true;
            }
        }

        // 读完了，还需要再读取新数据
        buf.compact();
        return false;
    }
}
