package me.test.jdk.java.net.socket;

import me.test.jdk.java.nio.ChannelTest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

/**
 * 使用 Socket API 发送 http 请求
 */
public class HttpPostTest {

    static final String MY_HOST = "ynuf.alipay.com";
    static final int MY_PORT = 80;
    static final String MY_PATH = "/service/um.json";

    public static void main(String[] args) throws IOException {

        Socket socket = getSocket();

        String reqData = getReqData();

        InetSocketAddress dest = new InetSocketAddress(MY_HOST, MY_PORT);
        socket.connect(dest);

        System.out.println("-------connected.");

        // 写：发送请求
        OutputStream out = socket.getOutputStream();
        out.write(reqData.getBytes("UTF-8"));
        System.out.println("-------request finished.");

        // 读：接受响应
        InputStream in = socket.getInputStream();

        ReadableByteChannel inChannel = Channels.newChannel(in);

        ByteBuffer buf = ByteBuffer.allocate(48);

        while (inChannel.read(buf) != -1) {
            buf.flip();
            while (buf.hasRemaining()) {
                System.out.print((char) buf.get());
            }
            buf.clear();
        }
        System.out.println("-------response finished.");

    }

    static String getReqData() throws UnsupportedEncodingException {
        String reqData = "k1=v1&k2=v2";

        StringBuilder buf = new StringBuilder();
        buf.append("POST ").append(MY_PATH).append(" HTTP/1.1\r\n");
        buf.append("Host: ").append(MY_HOST).append("\r\n");
        buf.append("Cache-Control: no-cache\r\n");
        buf.append("Pragma: no-cache\r\n");
        buf.append("User-Agent: HttpPostTest\r\n");
        buf.append("Accept: text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2\r\n");
        buf.append("Connection: keep-alive\r\n");
        buf.append("Content-Type: application/x-www-form-urlencoded; charset=UTF-8\r\n");
        buf.append("Content-Length: ").append(reqData.getBytes("UTF-8").length).append("\r\n");
        buf.append("\r\n");
        buf.append(reqData);
        return buf.toString();
    }


    static Socket getSocket() throws SocketException {

        Socket socket = new Socket();

        // true表示关闭Socket的缓冲,立即发送数据
        socket.setTcpNoDelay(true);

        // 表示是否允许重用Socket所绑定的本地地址
        socket.setReuseAddress(true);

        // 表示接收数据时的等待超时时间,单位毫秒..其默认值为0,表示会无限等待,永远不会超时
        socket.setSoTimeout(30000);

        // 表示当执行Socket.close()时,是否立即关闭底层的Socket
        // 这里设置为当Socket关闭后,底层Socket延迟5秒后再关闭,而5秒后所有未发送完的剩余数据也会被丢弃
        // 默认情况下,执行Socket.close()方法,该方法会立即返回,但底层的Socket实际上并不立即关闭
        // 它会延迟一段时间,直到发送完所有剩余的数据,才会真正关闭Socket,断开连接
        socket.setSoLinger(true, 5);

        // 表示是否支持发送一个字节的TCP紧急数据,socket.sendUrgentData(data)用于发送一个字节的TCP紧急数据
        // 其默认为false,即接收方收到紧急数据时不作任何处理,直接将其丢弃..若用户希望发送紧急数据,则应设其为true
        // 设为true后,接收方会把收到的紧急数据与普通数据放在同样的队列中
        socket.setOOBInline(true);

        // 该方法用于设置服务类型,以下代码请求高可靠性和最小延迟传输服务(把0x04与0x10进行位或运算)
        // Socket类用4个整数表示服务类型
        // 0x02:IPTOS_LOWCOST 低成本(二进制的倒数第二位为1)
        // 0x04:IPTOS_RELIABILITY 高可靠性(二进制的倒数第三位为1)
        // 0x08:IPTOS_THROUGHPUT 最高吞吐量(二进制的倒数第四位为1)
        // 0x10:IPTOS_LOWDELAY 最小延迟(二进制的倒数第五位为1)
        socket.setTrafficClass(0x04 | 0x10);

        // 该方法用于设定连接时间,延迟,带宽的相对重要性(该方法的三个参数表示网络传输数据的3项指标)
        // connectionTime   : 该参数表示用最少时间建立连接
        // latency          : 该参数表示最小延迟
        // bandwidth        : 该参数表示最高带宽
        // 可以为这些参数赋予任意整数值,这些整数之间的相对大小就决定了相应参数的相对重要性
        // 如这里设置的就是---最高带宽最重要,其次是最小连接时间,最后是最小延迟
        socket.setPerformancePreferences(2, 1, 3);

        return socket;
    }
}
