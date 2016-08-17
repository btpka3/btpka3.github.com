package me.test.jdk.java.nio;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

/**
 * 测试管道
 * a->A, B-B,
 * 0->1, 1->2 etc.
 */
public class PipeChannelTest {


    interface In {

        void setSource(Pipe.SourceChannel src);
    }

    interface Out {

        void setSink(Pipe.SinkChannel sink);
    }


    static class ToUpper implements Runnable, In, Out {
        Pipe.SourceChannel src;
        Pipe.SinkChannel sink;


        @Override
        public void run() {
            ByteBuffer buf = ByteBuffer.allocate(1024);

            try {
                while(-1!= src.read(buf)){

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

    static class ToNextDigit {

    }

    public static void main(String[] args) throws IOException {

        // InputStream in = new ByteBuffe
        ByteBuffer buf = ByteBuffer.allocate(48);


        Pipe pipe = Pipe.open();

        Pipe.SinkChannel sink = pipe.sink();

//        pipe.source()
//
//        PipedOutputStream out = new PipedOutputStream();
//        PipedInputStream in = new PipedInputStream(out);
//
//        R r = new R();
//        W w = new W();
//
//
//        ByteBuffer buf = ByteBuffer.allocate(1024);
//
//        r.in = in;
//        w.out = out;
//
//        r
//
//        r.start();
//        w.start();

    }


    static class R extends Thread {
        Pipe.SinkChannel sink;


        public void run() {

//
//            sink.write()
//            byte[] buf = new byte[1024];
//            int len;
//
//            try {
//                while ((len = in.read(buf)) != -1) {
//
//                    System.out.println(new String(buf, 0, len));
//
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                try {
//                    in.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            System.out.println("R end");
        }
    }

    static class W extends Thread {
        PipedOutputStream out = new PipedOutputStream();


        public void run() {
            long l = 0;
            try {
                while (l < 10) {
                    out.write(("W-" + l + " ;").getBytes());
                    l++;
                    Thread.sleep(1000);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("W end");
        }
    }
}
