package me.test.spring

import org.springframework.amqp.core.AmqpTemplate
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.ConfigurableApplicationContext

import static me.test.MY.*

// 参考：http://docs.spring.io/spring-amqp/docs/1.6.4.RELEASE/reference/html/
@EnableAutoConfiguration
@SpringBootApplication(scanBasePackages = ["me.test"])
class SpringAmqpPubTest {

    static void main(String[] args) {

        // Spring AMQP 与 Spring 框架紧密结合。故只能通过 ApplicationContext 来stop，参见最后调用stop代码。
        ConfigurableApplicationContext appContext = SpringApplication.run(SpringAmqpPubTest.class, args);

        AmqpTemplate template = appContext.getBean(AmqpTemplate)

        // 默认已经存在的预定义 exchange
        // AmqpAdmin amqpAdmin = appContext.getBean(AmqpAdmin)
        // amqpAdmin.declareExchange(new TopicExchange(AMQP_EXCHANGE_NAME, true, false))

        String routingKey = MQTT_TOPIC.replace('/', '.')

        println "start sending msg."
        MQTT_COUNT.times {
            String content = "SpringAmqpPubTest msg[$it]"
            template.convertAndSend(AMQP_EXCHANGE_NAME, routingKey, content);
        }
        println "msg has been sent."

        // factory.stop(); // WARNING: stop() is ignored unless the application context is being stopped
        appContext.stop()  // ContextStoppedEvent： 先停止所有 Lifecycle

        appContext.close() // ContextClosedEvent： 再销毁所有 bean
    }

}