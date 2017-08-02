package me.test.paho

import org.eclipse.paho.client.mqttv3.*
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence

import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory

import static me.test.MY.*

// 参考： https://github.com/eclipse/paho.mqtt.java
class PahoPubTest {

    // FIXME: ssh 隧道如何处理？即——SOCKSv5代理。有待尝试 testJDK 下面的代码。
    static void main(String[] args) {
        boolean useSsl = args.length > 0 && "ssl".equals(args[0]);
        String server = useSsl ? MQTT_SERVER_SSL : MQTT_SERVER


        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setUserName(MQTT_USER)
        connOpts.setPassword(MQTT_PWD.toCharArray())
        connOpts.setCleanSession(true);

        if (useSsl) {
            SSLContext sslContext = getClientSslContext(false)
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory()
            connOpts.setSocketFactory(sslSocketFactory)
        }


        String clientId = "PahoPubTest";
        MemoryPersistence persistence = new MemoryPersistence();

        try {
            MqttClient sampleClient = new MqttClient(server, clientId, persistence);



            System.out.println("Connecting to broker: " + server);
            sampleClient.connect(connOpts);
            System.out.println("Connected");

            MQTT_COUNT.times {
                String content = "PahoPubTest msg[$it]"
                MqttMessage message = new MqttMessage(content.getBytes());
                message.setQos(MQTT_QOS);
                sampleClient.publish(MQTT_TOPIC, message);
            }

            System.out.println("Message published");
            sampleClient.disconnect();
            System.out.println("Disconnected");

            System.exit(0);
        } catch (MqttException me) {
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();
        }
    }


}