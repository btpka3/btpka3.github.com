
package me.test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class MySSLSocketFactory extends SSLSocketFactory {

    public static class MyTrustManager implements X509TrustManager {

        public boolean isClientTrusted(X509Certificate[] chain) {

            return true;
        }

        public boolean isHostTrusted(X509Certificate[] chain) {

            return true;
        }

        public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {

        }

        public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {

        }

        public X509Certificate[] getAcceptedIssuers() {

            return null;
        }
    }

    private SSLSocketFactory sf;

    private static SSLSocketFactory instance;

    public MySSLSocketFactory() throws KeyManagementException, NoSuchAlgorithmException {

        SSLContext ctx = SSLContext.getInstance("SSL");
        ctx.init(null, new TrustManager[] { new MyTrustManager() }, new SecureRandom());
        sf = ctx.getSocketFactory();
    }

    public static synchronized SocketFactory getDefault() {

        if (instance == null) {
            try {
                instance = new MySSLSocketFactory();
            } catch (KeyManagementException e) {
                throw new RuntimeException(e);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }
        return instance;
    }

    @Override
    public Socket createSocket(Socket s, String host, int port, boolean autoClose) throws IOException {

        return sf.createSocket(s, host, port, autoClose);
    }

    @Override
    public String[] getDefaultCipherSuites() {

        return sf.getDefaultCipherSuites();
    }

    @Override
    public String[] getSupportedCipherSuites() {

        return sf.getSupportedCipherSuites();
    }

    @Override
    public Socket createSocket(String host, int port) throws IOException, UnknownHostException {

        return sf.createSocket(host, port);
    }

    @Override
    public Socket createSocket(InetAddress host, int port) throws IOException {

        return sf.createSocket(host, port);
    }

    @Override
    public Socket createSocket(String host, int port, InetAddress localHost, int localPort) throws IOException, UnknownHostException {

        return sf.createSocket(host, port, localHost, localPort);
    }

    @Override
    public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort) throws IOException {

        return sf.createSocket(address, port, localAddress, localPort);
    }
}
