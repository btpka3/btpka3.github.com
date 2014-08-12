package me.test.jdk.java.net.socket;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

public class MyEchoClient {

    public static void main(String[] args) throws UnknownHostException, IOException {

        byte[] userMsgs = null;
        if (args.length == 0) {

            // 模拟的用户输入
            userMsgs = new byte[] {
                    'I',
                    -26, -120, -111, // '我',
                    -26, // malformed
                    'I',
                    'I',
                    '.'
            };
        } else {
            userMsgs = args[0].getBytes(StandardCharsets.UTF_8);
        }

        if (userMsgs.length == 0) {

        }

        Socket socket = new Socket("localhost", 9999);
        new Thread(new EchoRespMsgHander(socket)).start();
        new Thread(new UserMsgHander(socket, userMsgs)).start();

    }

    /**
     * Read user input and sent it to echo server.
     */
    private static class UserMsgHander implements Runnable {

        private byte[] userMsgs;
        private Socket socket;

        public UserMsgHander(Socket socket, byte[] userMsgs) {
            this.socket = socket;
            this.userMsgs = userMsgs;
        }

        @Override
        public void run() {

            // send message to sever
            try {
                OutputStream out = socket.getOutputStream();
                for (byte b : userMsgs) {
                    out.write(b);
                    out.flush();
                    System.out.println(">>> '" + b + "'");
                    Thread.sleep(1000);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Read msg from echo server, and print it to stdout.
     */
    private static class EchoRespMsgHander implements Runnable {

        private Socket socket;

        public EchoRespMsgHander(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                Reader reader = new InputStreamReader(new BufferedInputStream(socket.getInputStream()), "UTF-8");
                int i;
                while ((i = reader.read()) != -1) {
                    char c = (char) i;
                    System.out.println("<<< '" + c + "'");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
