package me.test.jdk.java.rmi.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.rmi.server.RMIClientSocketFactory;

// http://docs.oracle.com/javase/1.4.2/docs/guide/rmi/socketfactory/index.html
// https://secure.kitserve.org.uk/content/setup-java-rmi-over-internet
public class MyRmiClientSocketFactory implements RMIClientSocketFactory {

    private int socketTimeout = 0;

    private int connectTimeout = 0;

    public Socket createSocket(String host, int port) throws IOException {
        final Socket socket = new Socket();
        socket.setSoTimeout(socketTimeout);
        socket.setSoLinger(false, 0);
        socket.connect(new InetSocketAddress(host, port), connectTimeout);
        return socket;

    }

    public int getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }
}
