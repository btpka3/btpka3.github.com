package me.test.jdk.java.net;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.junit.jupiter.api.Assertions;

/**
 * @author dangqian.zll
 * @date 2024/1/30
 */
public class URLServerMain {
    static String msg = "Hello from JDK 21 HTTP Server!";

    /**
     * JDK 21 最简 HTTP Server 示例
     * 访问路径: http://localhost:8080/test
     * 返回内容: Hello from JDK 21 HTTP Server!
     *
     * @see me.test.jdk.java.net.URLMain
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        // 创建 HTTP Server，监听 8080 端口
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        try {
            // 创建 /test 路径的处理器
            server.createContext("/test", exchange -> {
                String response = msg;
                exchange.sendResponseHeaders(200, response.getBytes().length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
            });

            // 启动服务器
            server.start();
            System.out.println("HTTP Server started at http://localhost:8080/test");

            // 使用 HttpClient 测试请求
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(java.net.URI.create("http://localhost:8080/test"))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            Assertions.assertEquals(200, response.statusCode());
            Assertions.assertEquals("Hello from JDK 21 HTTP Server!", response.body());

            Thread.sleep(60 * 60 * 1000);
        } finally {
            // 停止服务器
            server.stop(0);
        }
    }

}