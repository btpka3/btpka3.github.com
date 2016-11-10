package me.test.paho

import org.eclipse.paho.client.mqttv3.*
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence

import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory

import static me.test.MY.*

// 参考： https://github.com/eclipse/paho.mqtt.java/blob/master/org.eclipse.paho.client.mqttv3/src/main/java/org/eclipse/paho/client/mqttv3/internal/wire/MqttSubscribe.java
class PahoSubTest {

    static void main(String[] args) {

        SSLContext sslContext = getClientSslContext(false)
        SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory()

        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setUserName(MQTT_USER)
        connOpts.setPassword(MQTT_PWD.toCharArray())
        connOpts.setCleanSession(true);
        connOpts.setSocketFactory(sslSocketFactory)

        String clientId = "PahoSubTest";
        MemoryPersistence persistence = new MemoryPersistence();

        try {
            MqttClient sampleClient = new MqttClient(MQTT_SERVER, clientId, persistence);



            System.out.println("Connecting to broker: " + MQTT_SERVER);
            sampleClient.connect(connOpts);
            System.out.println("Connected");

            sampleClient.subscribe(MQTT_TOPIC);

            int i = -1;
            Thread mainThread = Thread.currentThread();

            sampleClient.setCallback(new MqttCallback() {

                public void messageArrived(String topic, MqttMessage message)
                        throws Exception {

                    System.out.println("messageArrived : " + new String(message.getPayload()));
                    i++
                    if (i >= MQTT_COUNT - 1) {
                        synchronized (mainThread) {
                            mainThread.notify();
                        }
                    }
                }

                // FIXME: 这个的具体含义是？
                public void deliveryComplete(IMqttDeliveryToken token) {
                    System.out.println("deliveryComplete : "
                            + token.getMessageId());
                }

                public void connectionLost(Throwable cause) {
                    System.out.println("connectionLost : " + cause);
                }
            });

            synchronized (mainThread) {
                mainThread.wait();
            }

            sampleClient.disconnect();
            System.out.println("Disconnected");

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