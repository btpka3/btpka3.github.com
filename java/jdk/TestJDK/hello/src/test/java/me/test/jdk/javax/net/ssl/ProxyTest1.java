package me.test.jdk.javax.net.ssl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

/**
 * 使用 Socket API 发送 http 请求
 */
public class ProxyTest1 {

    static final String TLS_ROOT_PATH = "/Users/zll/work/git-repo/github/btpka3"
            + "/btpka3.github.com/java/jdk/TestJDK/src/main/resources/me/test/jdk/javax/net/ssl/ProxyTest1";

    // 自签名证书 需要将 $JDK_HOME/jre/lib/security/cacerts 合并才行，否则 ： ValidatorException: No trusted certificate found
    static final String TLS_CA_STORE_PATH = TLS_ROOT_PATH + "/cacerts.added";
    static final String TLS_CA_STORE_TYPE = "JKS"; // "PKCS12"
    static final String TLS_CA_STORE_PASS = "changeit"; // "changeit"

    // 开启 ssh 代理
    // ssh root@192.168.0.12 -C -f -N -g -D 9999
    static final Proxy proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("localhost", 9999));

    // 3.
    // 4.
    // 5. 有代理访问自签名证书网址 https
    public static void main(String[] args)
            throws IOException, CertificateException, NoSuchAlgorithmException, KeyStoreException,
                    KeyManagementException {
        // System.setProperty("javax.net.debug", "ssl,handshake");
        //        test1_4(4);
        test5_8(8);
    }

    static void test1_4(int i)
            throws CertificateException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException,
                    IOException {

        Socket socket = null;

        switch (i) {
            case 2:
                socket = getSocket2();
                break;
            case 3:
                socket = getSocket3();
                break;
            case 4:
                socket = getSocket4();
                break;
            default:
                socket = getSocket1();
                break;
        }

        // 写：发送请求
        String reqData = "" + "GET /service/um.json HTTP/1.1\r\n"
                + "Host: ynuf.alipay.com\r\n"
                + "Cache-Control: no-cache\r\n"
                + "User-Agent: ProxyTest1\r\n"
                + "Accept: text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2\r\n"
                + "Connection: keep-alive\r\n"
                + "\r\n";

        OutputStream out = socket.getOutputStream();
        out.write(reqData.getBytes(StandardCharsets.UTF_8));
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
        System.out.println("\n-------response finished.");
    }

    static void test5_8(int i)
            throws CertificateException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException,
                    IOException {

        Socket socket = null;

        switch (i) {
            case 6:
                socket = getSocket6();
                break;
            case 7:
                socket = getSocket7();
                break;
            case 8:
                socket = getSocket8();
                break;
            default:
                socket = getSocket5();
                break;
        }

        // 写：发送请求
        String reqData = "" + "GET / HTTP/1.1\r\n"
                + "Host: test.me\r\n"
                + "Cache-Control: no-cache\r\n"
                + "User-Agent: ProxyTest1\r\n"
                + "Accept: text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2\r\n"
                + "Connection: keep-alive\r\n"
                + "\r\n";

        OutputStream out = socket.getOutputStream();
        out.write(reqData.getBytes(StandardCharsets.UTF_8));
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
        System.out.println("\n-------response finished.");
    }

    static Socket getSocket1()
            throws CertificateException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException,
                    IOException {

        System.out.println("========================================= test 1 : 直接(无代理)访问外网 http");

        // http://ynuf.alipay.com/service/um.json
        // HTTP 协议，不能使用 SSLSocket ，否则：SSLException: Unrecognized SSL message, plaintext connection?
        InetSocketAddress dest = new InetSocketAddress("ynuf.alipay.com", 80);

        //        SSLSocketFactory sslSocketFactory = getClientSslContext(false).getSocketFactory();
        //        Socket socket  = sslSocketFactory.createSocket();
        Socket socket = new Socket();

        socket.connect(dest);

        return socket;
    }

    static Socket getSocket2()
            throws CertificateException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException,
                    IOException {

        System.out.println("========================================= test 2 : 直接(无代理)访问外网 https");

        // https://ynuf.alipay.com/service/um.json
        InetSocketAddress dest = new InetSocketAddress("ynuf.alipay.com", 443);

        SSLSocketFactory sslSocketFactory = getClientSslContext(false).getSocketFactory();
        Socket socket = sslSocketFactory.createSocket();

        socket.connect(dest);

        return socket;
    }

    // http://stackoverflow.com/questions/5783832/socks5-proxy-using-sslsocket
    static Socket getSocket3()
            throws CertificateException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException,
                    IOException {

        System.out.println("========================================= test 3 : 有代理访问外网网址 http");

        // http://ynuf.alipay.com/service/um.json
        InetSocketAddress dest = new InetSocketAddress("ynuf.alipay.com", 80);

        Socket socket = new Socket(proxy);
        socket.connect(dest);

        return socket;
    }

    static Socket getSocket4()
            throws CertificateException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException,
                    IOException {

        System.out.println("========================================= test 4 : 有代理访问外网 https");

        SSLSocketFactory sslSocketFactory = getClientSslContext(false).getSocketFactory();

        // https://ynuf.alipay.com/service/um.json
        InetSocketAddress dest = new InetSocketAddress("ynuf.alipay.com", 443);

        Socket underlying = new Socket(proxy);
        underlying.connect(dest);

        InetSocketAddress addr = (InetSocketAddress) proxy.address();
        String proxyHost = addr.getHostName();
        int proxyPort = addr.getPort();

        Socket socket = sslSocketFactory.createSocket(underlying, proxyHost, proxyPort, true);

        return socket;
    }

    static Socket getSocket5()
            throws CertificateException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException,
                    IOException {

        System.out.println("========================================= test 5 : 无代理访问自签名 https");

        SSLSocketFactory sslSocketFactory = getClientSslContext(false).getSocketFactory();

        // https://test.me/
        InetSocketAddress dest = new InetSocketAddress("192.168.0.41", 443);

        Socket socket = sslSocketFactory.createSocket();
        socket.connect(dest);

        return socket;
    }

    static Socket getSocket6()
            throws CertificateException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException,
                    IOException {

        System.out.println("========================================= test 6 : 有代理访问自签名 https");

        SSLSocketFactory sslSocketFactory = getClientSslContext(false).getSocketFactory();

        // https://test.me/
        InetSocketAddress dest = new InetSocketAddress("192.168.0.41", 443);

        Socket underlying = new Socket(proxy);
        underlying.connect(dest);

        InetSocketAddress addr = (InetSocketAddress) proxy.address();
        String proxyHost = addr.getHostName();
        int proxyPort = addr.getPort();

        Socket socket = sslSocketFactory.createSocket(underlying, proxyHost, proxyPort, true);

        return socket;
    }

    static Socket getSocket7()
            throws CertificateException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException,
                    IOException {

        System.out.println("========================================= test 7 : 无代理访问自签名 https (MySSLSocketFactory)");

        SSLSocketFactory sslSocketFactory = getSSLSocketFactory(getClientSslContext(false), null);

        // https://test.me/
        InetSocketAddress dest = new InetSocketAddress("192.168.0.41", 443);

        Socket socket = sslSocketFactory.createSocket();
        socket.connect(dest);

        return socket;
    }

    static Socket getSocket8()
            throws CertificateException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException,
                    IOException {

        System.out.println("========================================= test 8 : 有代理访问自签名 https (MySSLSocketFactory)");

        SSLSocketFactory sslSocketFactory = getSSLSocketFactory(getClientSslContext(false), proxy);

        // https://test.me/
        InetSocketAddress dest = new InetSocketAddress("192.168.0.41", 443);

        Socket socket = sslSocketFactory.createSocket();
        socket.connect(dest);

        return socket;
    }

    static SSLContext getClientSslContext(boolean useClientCert)
            throws IOException, KeyStoreException, CertificateException, NoSuchAlgorithmException,
                    KeyManagementException {

        // 配置 TrustManager (只需要CA的证书，故不需要CA私钥的密码）
        char[] caStorePass = TLS_CA_STORE_PASS.toCharArray();
        KeyStore caKs = KeyStore.getInstance(TLS_CA_STORE_TYPE);
        caKs.load(new FileInputStream(TLS_CA_STORE_PATH), caStorePass);

        TrustManagerFactory caTmf = TrustManagerFactory.getInstance("SunX509");
        caTmf.init(caKs);

        //        // 加载 client 的 KeyManager
        //        char[] clientStorePass = TLS_CLIENT_STORE_PASS.toCharArray();
        //        KeyStore clientKeyStore = KeyStore.getInstance(TLS_CLIENT_STORE_TYPE);
        //        clientKeyStore.load(new FileInputStream(TLS_CLIENT_STORE_PATH), clientStorePass);
        //
        //        char[] clientKeyPass = TLS_CLIENT_KEY_PASS.toCharArray()
        //        KeyManagerFactory clientKmf = KeyManagerFactory.getInstance("SunX509");
        //        clientKmf.init(clientKeyStore, clientKeyPass);

        SSLContext sslContext = SSLContext.getInstance("TLSv1.2");

        //        if (useClientCert) {
        //            sslContext.init(clientKmf.getKeyManagers(), caTmf.getTrustManagers(), null); // 使用 client 端证书
        //        } else {
        //            sslContext.init(null, caTmf.getTrustManagers(), null); // 不使用 client 端证书
        //        }
        sslContext.init(null, caTmf.getTrustManagers(), null); // 不使用 client 端证书
        //        sslContext.init(null, null, null); // 不使用 client 端证书
        return sslContext;
    }

    // proxy 可以为 null
    static MySSLSocketFactory getSSLSocketFactory(SSLContext sslContext, Proxy proxy) {
        MySSLSocketFactory fac = new MySSLSocketFactory();
        fac.setSSLSocketFacotry(sslContext.getSocketFactory());
        fac.setProxy(proxy);
        return fac;
    }
}

/* ------------------------------------ 生成证书

# 生成自签名证书
keytool -genkeypair \
    -alias nginx \
    -keyalg RSA \
    -keysize 2048 \
    -sigalg SHA1withRSA \
    -dname "CN=test.me" \
    -validity 3650 \
    -keypass 456789 \
    -keystore test.me.jks \
    -storepass 123456


# 将 KeyStore 变更为 PKCS#12 格式
keytool -importkeystore \
    -srcstoretype JKS \
    -srckeystore test.me.jks \
    -srcstorepass 123456 \
    -srcalias nginx \
    -srckeypass 456789 \
    -deststoretype PKCS12 \
    -destkeystore test.me.p12 \
    -deststorepass 123456 \
    -destalias nginx \
    -destkeypass 123456 \
    -noprompt

# 导出证书
openssl pkcs12 \
    -in test.me.p12 \
    -passin pass:123456 \
    -nokeys \
    -out test.me.pem.cer

# 导出私钥
openssl pkcs12 \
    -in test.me.p12  \
    -passin pass:123456 \
    -nodes \
    -nocerts \
    -out test.me.pem.key

# list、merge
keytool -list -keystore test.me.jks
cp $JAVA_HOME/jre/lib/security/cacerts ./cacerts.added
keytool -list -keystore test.me.jks

# password: changeit
keytool -keystore cacerts.added -alias nginx -importcert -file test.me.pem.cer
keytool -list -keystore cacerts.added


# 修改 /etc/hosts 配置 test.me 域名为自己的ip地址。
*/
