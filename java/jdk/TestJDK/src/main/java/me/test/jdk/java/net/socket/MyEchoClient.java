package me.test.jdk.java.net.socket;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.Socket;
import java.net.UnknownHostException;

public class MyEchoClient {

    public static void main(String[] args) throws UnknownHostException, IOException {

        String[] userMsgs = args;
        if (userMsgs.length == 0) {
            userMsgs = new String[] { "hi 中国" };
        }

        Socket socket = new Socket("localhost", 9999);
        new Thread(new EchoRespMsgHander(socket)).start();
        new Thread(new UserMsgHander(socket, userMsgs)).start();

    }

    /**
     * Read user input and sent it to echo server.
     */
    private static class UserMsgHander implements Runnable {

        private String[] userMsgs;
        private Socket socket;

        public UserMsgHander(Socket socket, String[] userMsgs) {
            this.socket = socket;
            this.userMsgs = userMsgs;
        }
//
//        @Override
//        public void run() {
//
//            // send message to sever
//            Writer writer;
//            try {
//                writer = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
//
//                for (String arg : userMsgs) {
//                    for (char c : arg.toCharArray()) {
//                        writer.write(c);
//                        writer.flush();
//                        System.out.println(">>> '" + c + "'");
//                    }
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        

        @Override
        public void run() {

            // send message to sever
            OutputStream out;
            try {
                out = socket.getOutputStream();

                for (String arg : userMsgs) {
                    for (byte b : arg.getBytes("UTF-8")) {
                        out.write(b);
                        out.flush();
                        System.out.println(">>> '" + b + "'");
                        Thread.sleep(1000);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
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
