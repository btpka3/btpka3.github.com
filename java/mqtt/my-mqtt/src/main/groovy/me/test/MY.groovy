package me.test

import org.apache.http.conn.ssl.SSLConnectionSocketFactory

import javax.net.ssl.KeyManagerFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import java.security.KeyStore;


public class MY {

    // static final String MQTT_SERVER = "tcp://iot.eclipse.org:1883"
    static final String MQTT_SERVER = "tcp://localhost:1883"
    static final String MQTT_SERVER_SSL = "ssl://localhost:8883"
    static final String MQTT_TOPIC = "/my/mqtt"
    static final int MQTT_QOS = 0
    static final int MQTT_COUNT = 10
    static final String MQTT_USER = "guest"
    static final String MQTT_PWD = "guest"

    // -----------
    static final String AMQP_HOST = "localhost"
    static final int AMQP_PORT = 5672
    static final int AMQP_PORT_SSL = 5671

    static final String AMQP_VIRTUAL_HOST = "/"
    static final String AMQP_EXCHANGE_NAME = "amq.topic"
    static final String AMQP_QUEUE_NAME = ""

    // -----------
    static final String TLS_ROOT_PATH = "/Users/zll/work/git-repo/github/btpka3/btpka3.github.com/java/mqtt/my-mqtt/src/main/resources"

    static final String TLS_CA_STORE_PATH = TLS_ROOT_PATH + "/myca.jks";
    static final String TLS_CA_STORE_TYPE = "JKS"; // "PKCS12"
    static final String TLS_CA_STORE_PASS = "123456"

    static final String TLS_SERVER_STORE_PATH = TLS_ROOT_PATH + "/server.jks";
    static final String TLS_SERVER_STORE_TYPE = "JKS"; // "PKCS12"
    static final String TLS_SERVER_STORE_PASS = "123456"
    static final String TLS_SERVER_KEY_PASS = "456789"

    static final String TLS_CLIENT_STORE_PATH = TLS_ROOT_PATH + "/client.jks";
    static final String TLS_CLIENT_STORE_TYPE = "JKS"; // "PKCS12"
    static final String TLS_CLIENT_STORE_PASS = "123456"
    static final String TLS_CLIENT_KEY_PASS = "456789"

    static SSLContext getClientSslContext(boolean useClientCert) {
        // 配置 TrustManager (只需要CA的证书，故不需要CA私钥的密码）
        char[] caStorePass = TLS_CA_STORE_PASS.toCharArray();
        KeyStore caKs = KeyStore.getInstance(TLS_CA_STORE_TYPE);
        caKs.load(new FileInputStream(TLS_CA_STORE_PATH), caStorePass);

        TrustManagerFactory caTmf = TrustManagerFactory.getInstance("SunX509");
        caTmf.init(caKs);

        // 加载 client 的 KeyManager
        char[] clientStorePass = TLS_CLIENT_STORE_PASS.toCharArray();
        KeyStore clientKeyStore = KeyStore.getInstance(TLS_CLIENT_STORE_TYPE);
        clientKeyStore.load(new FileInputStream(TLS_CLIENT_STORE_PATH), clientStorePass);

        char[] clientKeyPass = TLS_CLIENT_KEY_PASS.toCharArray()
        KeyManagerFactory clientKmf = KeyManagerFactory.getInstance("SunX509");
        clientKmf.init(clientKeyStore, clientKeyPass);

        SSLContext sslContext = SSLContext.getInstance("TLSv1.2");

        if (useClientCert) {
            sslContext.init(clientKmf.getKeyManagers(), caTmf.getTrustManagers(), null); // 使用 client 端证书
        } else {
            sslContext.init(null, caTmf.getTrustManagers(), null); // 不使用 client 端证书
        }
        return sslContext
    }


    static void dd(){
        SSLContext sslContext = getClientSslContext(false)
        sslContext.getSocketFactory()

//        SSLConnectionSocketFactory wxSslConnFac = new SSLConnectionSocketFactoryEx(
//                sslContext,
//                ["TLSv1"] as String[],
//                null as String[],
//                BrowserCompatHostnameVerifier.INSTANCE);
    }
}
