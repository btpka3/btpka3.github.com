package me.test;

import org.apache.http.HttpHost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContexts;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

/**
 *
 */
public class TestSocksProxy {

    public static void main(String[] args) throws KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyManagementException, IOException, CertificateException {
        // 在 192.168.0.12 上执行
        // ssh root@192.168.0.12 -C -f -N -g -D 9999
        Proxy proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("192.168.0.12", 9999));

        HttpHost proxy1 = HttpHost.create("socks://192.168.0.12:9999");

        PlainConnectionSocketFactoryEx httpConnFac = new PlainConnectionSocketFactoryEx();
        httpConnFac.setProxy(proxy);
//
//        KeyStore wxKeyStore = KeyStore.getInstance("PKCS12");
//        wxKeyStore.load(new FileInputStream("xxx.p12"), "ks-pass".toCharArray());
        SSLContext sslcontext = SSLContexts.custom()
//                .loadKeyMaterial(wxKeyStore, "privateKeyPass".toCharArray())
                .build();
        SSLConnectionSocketFactoryEx httpsConnFac = new SSLConnectionSocketFactoryEx(
                sslcontext,
                new String[]{"TLSv1"},
                null,
                new DefaultHostnameVerifier());
        httpsConnFac.setProxy(proxy);

        Registry<ConnectionSocketFactory> reg = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", httpConnFac)
                .register("https", httpsConnFac)
                .build();

        BasicHttpClientConnectionManager connMgr = new BasicHttpClientConnectionManager(reg);
        CloseableHttpClient httpCient = HttpClients.custom()
                .setConnectionManager(connMgr)
                .build();

        HttpComponentsClientHttpRequestFactory reqFac = new HttpComponentsClientHttpRequestFactory(httpCient);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(reqFac);


        String url = "http://kingsilk.net/qh/mall/";
        String respStr = restTemplate.getForObject(url, String.class);
        System.out.println(respStr);

        url = "https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/activity/js/activity_start_52498d2c.js";
        respStr = restTemplate.getForObject(url, String.class);
        System.out.println(respStr);
    }

    public static class PlainConnectionSocketFactoryEx extends PlainConnectionSocketFactory {
        private Proxy proxy;

        public PlainConnectionSocketFactoryEx() {
            super();
        }

        @Override
        public Socket createSocket(final HttpContext context) throws IOException {
            if (proxy != null) {
                System.out.println("---------------");
                return new Socket(proxy);
            }
            return super.createSocket(context);
        }

        public void setProxy(Proxy proxy) {
            this.proxy = proxy;
        }
    }

    public static class SSLConnectionSocketFactoryEx extends SSLConnectionSocketFactory {

        private Proxy proxy;

        public SSLConnectionSocketFactoryEx(SSLContext sslContext) {
            super(sslContext);
        }

        public SSLConnectionSocketFactoryEx(SSLContext sslContext, X509HostnameVerifier hostnameVerifier) {
            super(sslContext, hostnameVerifier);
        }

        public SSLConnectionSocketFactoryEx(SSLContext sslContext, String[] supportedProtocols, String[] supportedCipherSuites, X509HostnameVerifier hostnameVerifier) {
            super(sslContext, supportedProtocols, supportedCipherSuites, hostnameVerifier);
        }

        public SSLConnectionSocketFactoryEx(SSLSocketFactory socketfactory, X509HostnameVerifier hostnameVerifier) {
            super(socketfactory, hostnameVerifier);
        }

        public SSLConnectionSocketFactoryEx(SSLSocketFactory socketfactory, String[] supportedProtocols, String[] supportedCipherSuites, X509HostnameVerifier hostnameVerifier) {
            super(socketfactory, supportedProtocols, supportedCipherSuites, hostnameVerifier);
        }

        public SSLConnectionSocketFactoryEx(SSLContext sslContext, HostnameVerifier hostnameVerifier) {
            super(sslContext, hostnameVerifier);
        }

        public SSLConnectionSocketFactoryEx(SSLContext sslContext, String[] supportedProtocols, String[] supportedCipherSuites, HostnameVerifier hostnameVerifier) {
            super(sslContext, supportedProtocols, supportedCipherSuites, hostnameVerifier);
        }

        public SSLConnectionSocketFactoryEx(SSLSocketFactory socketfactory, HostnameVerifier hostnameVerifier) {
            super(socketfactory, hostnameVerifier);
        }

        public SSLConnectionSocketFactoryEx(SSLSocketFactory socketfactory, String[] supportedProtocols, String[] supportedCipherSuites, HostnameVerifier hostnameVerifier) {
            super(socketfactory, supportedProtocols, supportedCipherSuites, hostnameVerifier);
        }

        @Override
        public Socket createSocket(final HttpContext context) throws IOException {
            if (proxy != null) {
                System.out.println("================");
                return new Socket(proxy);
            }
            return super.createSocket(context);
        }

        public void setProxy(Proxy proxy) {
            this.proxy = proxy;
        }

    }

}
