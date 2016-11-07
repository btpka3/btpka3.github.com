package me.test.paho

import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttException
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence

import static me.test.MY.*

// 参考： https://github.com/eclipse/paho.mqtt.java
class PahoPubTest {


    static void main(String[] args) {
        String clientId = "PahoPubTest";
        MemoryPersistence persistence = new MemoryPersistence();

        try {
            MqttClient sampleClient = new MqttClient(MQTT_SERVER, clientId, persistence);

            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setUserName(MQTT_USER)
            connOpts.setPassword(MQTT_PWD.toCharArray())
            connOpts.setCleanSession(true);

            System.out.println("Connecting to broker: " + MQTT_SERVER);
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