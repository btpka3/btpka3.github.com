package me.test.jdk.java.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

/**
 * 测试管道
 * <p>
 * R 负责读取(模拟数据), ToUpper 负责数据处理 ( 'a' -> 'A' ), W 负责结果输出(控制台)
 */
public class PipeChannelTest {

    interface In {

        void setSource(Pipe.SourceChannel src);
    }

    interface Out {

        void setSink(Pipe.SinkChannel sink);
    }

    static class R implements Runnable, Out {

        Pipe.SinkChannel sink;

        @Override
        public void run() {
            ByteBuffer buf = ByteBuffer.allocate(5);
            try {
                final int COUNT = 11;
                int c = 0;
                while (c < COUNT) {

                    // 生成长度为N数据
                    for (int i = buf.position(); i < buf.limit() && c < COUNT; i++) {
                        System.out.println("R : " + i);
                        c++;
                        buf.put((byte) 'a');
                    }

                    buf.flip();
                    sink.write(buf);
                    buf.compact();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void setSink(Pipe.SinkChannel sink) {
            this.sink = sink;
        }
    }

    static class W implements Runnable, In {
        Pipe.SourceChannel src;

        @Override
        public void run() {
            ByteBuffer buf = ByteBuffer.allocate(6);

            try {
                while (src.read(buf) != -1) {
                    buf.flip();
                    while (buf.hasRemaining()) {
                        int i = buf.position();
                        System.out.println("W    : " + i + " : " + ((char) buf.get()));
                    }
                    buf.compact();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void setSource(Pipe.SourceChannel src) {
            this.src = src;
        }
    }

    static class ToUpper implements Runnable, In, Out {
        Pipe.SourceChannel src;
        Pipe.SinkChannel sink;

        @Override
        public void run() {
            ByteBuffer buf = ByteBuffer.allocate(4);

            byte diff = 'a' - 'A';
            try {
                while (-1 != src.read(buf)) {
                    buf.flip();
                    buf.mark();
                    while (buf.hasRemaining()) {
                        int i = buf.position();
                        byte b = buf.get();
                        System.out.println("T        : " + i + " : " + ((char) b));
                        if (b >= 'a' && b <= 'z') {
                            byte upper = (byte) (b - diff);
                            buf.put(i, upper);
                        }
                    }
                    buf.reset();
                    sink.write(buf);
                    buf.compact();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void setSource(Pipe.SourceChannel src) {
            this.src = src;
        }

        @Override
        public void setSink(Pipe.SinkChannel sink) {
            this.sink = sink;
        }
    }

    public static void main(String[] args) throws IOException {

        //        test1();
        test2();
    }

    public static void test1() throws IOException {
        R r = new R();
        W w = new W();
        Pipe pipe = Pipe.open();
        r.setSink(pipe.sink());
        w.setSource(pipe.source());

        new Thread(r).start();
        new Thread(w).start();
    }

    public static void test2() throws IOException {

        R r = new R();
        ToUpper t = new ToUpper();
        W w = new W();

        Pipe p1 = Pipe.open();
        r.setSink(p1.sink());
        t.setSource(p1.source());

        Pipe p2 = Pipe.open();
        t.setSink(p2.sink());
        w.setSource(p2.source());

        new Thread(r).start();
        new Thread(t).start();
        new Thread(w).start();
    }
}
