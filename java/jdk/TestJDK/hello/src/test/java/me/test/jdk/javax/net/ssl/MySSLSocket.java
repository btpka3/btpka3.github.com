package me.test.jdk.javax.net.ssl;

import javax.net.ssl.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.nio.channels.SocketChannel;

/**
 * 因为使用ssl隧道不放方便，故增加此类。
 *
 * 要求：获得该类之后，必须先调用connect方法，之后才能调用其他方法。
 */
public class MySSLSocket extends Socket {

    private Socket underlyingSocket;
    private Socket socket;
    private Proxy proxy;
    private SSLSocketFactory sslSocketFactory;

    public void setUnderlyingSocket(Socket underlyingSocket) {
        this.underlyingSocket = underlyingSocket;
    }

    public void setProxy(Proxy proxy) {
        this.proxy = proxy;
    }

    public void setSslSocketFactory(SSLSocketFactory sslSocketFactory) {
        this.sslSocketFactory = sslSocketFactory;
    }

    private void checkProxy() throws IOException {
        if (this.proxy != null && sslSocketFactory != null) {
            InetSocketAddress addr = (InetSocketAddress) proxy.address();
            String proxyHost = addr.getHostName();
            int proxyPort = addr.getPort();
            socket = sslSocketFactory.createSocket(underlyingSocket, proxyHost, proxyPort, true);
        }
    }

    // -------------- 以下为【修改过】的代理方法
    @Override
    public void connect(SocketAddress endpoint) throws IOException {

        underlyingSocket.connect(endpoint);
        checkProxy();

    }

    @Override
    public void connect(SocketAddress endpoint, int timeout) throws IOException {
        underlyingSocket.connect(endpoint, timeout);
        checkProxy();
    }

    // -------------- 以下为未做修改的代理方法
    @Override
    public void bind(SocketAddress bindpoint) throws IOException {
        socket.bind(bindpoint);
    }

    @Override
    public InetAddress getInetAddress() {
        return socket.getInetAddress();
    }

    @Override
    public InetAddress getLocalAddress() {
        return socket.getLocalAddress();
    }

    @Override
    public int getPort() {
        return socket.getPort();
    }

    @Override
    public int getLocalPort() {
        return socket.getLocalPort();
    }

    @Override
    public SocketAddress getRemoteSocketAddress() {
        return socket.getRemoteSocketAddress();
    }

    @Override
    public SocketAddress getLocalSocketAddress() {
        return socket.getLocalSocketAddress();
    }

    @Override
    public SocketChannel getChannel() {
        return socket.getChannel();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return socket.getInputStream();
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return socket.getOutputStream();
    }

    @Override
    public void setTcpNoDelay(boolean on) throws SocketException {
        socket.setTcpNoDelay(on);
    }

    @Override
    public boolean getTcpNoDelay() throws SocketException {
        return socket.getTcpNoDelay();
    }

    @Override
    public void setSoLinger(boolean on, int linger) throws SocketException {
        socket.setSoLinger(on, linger);
    }

    @Override
    public int getSoLinger() throws SocketException {
        return socket.getSoLinger();
    }

    @Override
    public void sendUrgentData(int data) throws IOException {
        socket.sendUrgentData(data);
    }

    @Override
    public void setOOBInline(boolean on) throws SocketException {
        socket.setOOBInline(on);
    }

    @Override
    public boolean getOOBInline() throws SocketException {
        return socket.getOOBInline();
    }

    @Override
    public void setSoTimeout(int timeout) throws SocketException {
        socket.setSoTimeout(timeout);
    }

    @Override
    public int getSoTimeout() throws SocketException {
        return socket.getSoTimeout();
    }

    @Override
    public void setSendBufferSize(int size) throws SocketException {
        socket.setSendBufferSize(size);
    }

    @Override
    public int getSendBufferSize() throws SocketException {
        return socket.getSendBufferSize();
    }

    @Override
    public void setReceiveBufferSize(int size) throws SocketException {
        socket.setReceiveBufferSize(size);
    }

    @Override
    public int getReceiveBufferSize() throws SocketException {
        return socket.getReceiveBufferSize();
    }

    @Override
    public void setKeepAlive(boolean on) throws SocketException {
        socket.setKeepAlive(on);
    }

    @Override
    public boolean getKeepAlive() throws SocketException {
        return socket.getKeepAlive();
    }

    @Override
    public void setTrafficClass(int tc) throws SocketException {
        socket.setTrafficClass(tc);
    }

    @Override
    public int getTrafficClass() throws SocketException {
        return socket.getTrafficClass();
    }

    @Override
    public void setReuseAddress(boolean on) throws SocketException {
        socket.setReuseAddress(on);
    }

    @Override
    public boolean getReuseAddress() throws SocketException {
        return socket.getReuseAddress();
    }

    @Override
    public void close() throws IOException {
        socket.close();
    }

    @Override
    public void shutdownInput() throws IOException {
        socket.shutdownInput();
    }

    @Override
    public void shutdownOutput() throws IOException {
        socket.shutdownOutput();
    }

    @Override
    public String toString() {
        return socket.toString();
    }

    @Override
    public boolean isConnected() {
        return socket.isConnected();
    }

    @Override
    public boolean isBound() {
        return socket.isBound();
    }

    @Override
    public boolean isClosed() {
        return socket.isClosed();
    }

    @Override
    public boolean isInputShutdown() {
        return socket.isInputShutdown();
    }

    @Override
    public boolean isOutputShutdown() {
        return socket.isOutputShutdown();
    }

    public static void setSocketImplFactory(SocketImplFactory fac) throws IOException {
        Socket.setSocketImplFactory(fac);
    }

    @Override
    public void setPerformancePreferences(int connectionTime, int latency, int bandwidth) {
        socket.setPerformancePreferences(connectionTime, latency, bandwidth);
    }
}
