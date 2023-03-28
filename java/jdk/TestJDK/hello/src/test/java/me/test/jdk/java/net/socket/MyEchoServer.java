package me.test.jdk.java.net.socket;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MyEchoServer {

    public static void main(String[] args) throws IOException {
        new Thread(new Manager()).run();
    }

    /**
     * 启动服务端监听socket，并将新的连接交由独立线程进行处理。
     */
    private static class Manager implements Runnable {

        @Override
        public void run() {

            ExecutorService exec = new ThreadPoolExecutor(3, 20, 1, TimeUnit.MINUTES,
                    new LinkedBlockingQueue<Runnable>());
            ServerSocket serverSocket;
            try {
                serverSocket = new ServerSocket(9999);
                Socket clientSocket;
                while ((clientSocket = serverSocket.accept()) != null) {
                    exec.execute(new Worker(clientSocket));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            exec.shutdown();
        }
    }

    /**
     * 处理一个connection上的消息。
     */
    private static class Worker implements Runnable {

        private Socket socket;

        public Worker(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                Reader reader = new InputStreamReader(new BufferedInputStream(socket.getInputStream()), "UTF-8");
                Writer writer = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
                int i = 0;

                // blocking read
                while ((i = reader.read()) != -1) {
                    char c = (char) i;
                    System.out.println("<<< " + socket + " : '" + c + "'");
                    writer.write(c);
                    writer.flush();
                    System.out.println(">>> " + socket + " : '" + c + "'");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
