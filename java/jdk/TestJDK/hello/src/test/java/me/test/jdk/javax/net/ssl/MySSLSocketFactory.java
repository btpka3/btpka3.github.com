package me.test.jdk.javax.net.ssl;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Proxy;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.net.ssl.SSLSocketFactory;

/**
 * 额外提供代理服务器的设置
 * <p>
 * 参考 : http://docs.oracle.com/javase/7/docs/technotes/guides/net/proxies.html
 */
public class MySSLSocketFactory extends SSLSocketFactory {

    private Proxy proxy;

    SSLSocketFactory sslSocketFactory;

    @Override
    public Socket createSocket() throws IOException {

        if (this.proxy != null) {
            MySSLSocket mySSLSocket = new MySSLSocket();
            mySSLSocket.setProxy(proxy);
            mySSLSocket.setUnderlyingSocket(new Socket(proxy));
            mySSLSocket.setSslSocketFactory(this);

            return mySSLSocket;
        }
        return sslSocketFactory.createSocket();
    }

    // -------------- 以下为未做修改的代理方法
    public void setSSLSocketFacotry(SSLSocketFactory sslSocketFactory) {
        this.sslSocketFactory = sslSocketFactory;
    }

    public void setProxy(Proxy proxy) {
        this.proxy = proxy;
    }

    @Override
    public String[] getDefaultCipherSuites() {
        return sslSocketFactory.getDefaultCipherSuites();
    }

    @Override
    public String[] getSupportedCipherSuites() {
        return sslSocketFactory.getSupportedCipherSuites();
    }

    @Override
    public Socket createSocket(Socket socket, String s, int i, boolean b) throws IOException {
        return sslSocketFactory.createSocket(socket, s, i, b);
    }

    @Override
    public Socket createSocket(Socket socket, InputStream inputStream, boolean b) throws IOException {
        return sslSocketFactory.createSocket(socket, inputStream, b);
    }

    @Override
    public Socket createSocket(String s, int i) throws IOException, UnknownHostException {
        return sslSocketFactory.createSocket(s, i);
    }

    @Override
    public Socket createSocket(String s, int i, InetAddress inetAddress, int i1)
            throws IOException, UnknownHostException {
        return sslSocketFactory.createSocket(s, i, inetAddress, i1);
    }

    @Override
    public Socket createSocket(InetAddress inetAddress, int i) throws IOException {
        return sslSocketFactory.createSocket(inetAddress, i);
    }

    @Override
    public Socket createSocket(InetAddress inetAddress, int i, InetAddress inetAddress1, int i1) throws IOException {
        return sslSocketFactory.createSocket(inetAddress, i, inetAddress1, i1);
    }
}
