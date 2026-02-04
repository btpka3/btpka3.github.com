package me.test.jdk.java.io;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * 测试管道
 */
public class PipeTest {

    public static void main(String[] args) throws IOException {

        PipedOutputStream out = new PipedOutputStream();
        PipedInputStream in = new PipedInputStream(out);

        R r = new R();
        W w = new W();

        r.in = in;
        w.out = out;

        r.start();
        w.start();
    }

    static class R extends Thread {
        PipedInputStream in = null;

        public void run() {
            byte[] buf = new byte[1024];
            int len;

            try {
                while ((len = in.read(buf)) != -1) {

                    System.out.println(new String(buf, 0, len));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("R end");
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
