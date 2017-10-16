package me.test.spring

import org.springframework.amqp.core.*
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer
import org.springframework.beans.BeansException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.*
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

import static me.test.MY.AMQP_EXCHANGE_NAME
import static me.test.MY.MQTT_TOPIC

// 参考：http://docs.spring.io/spring-amqp/docs/1.6.4.RELEASE/reference/html/
// 运行时需要指定 JVM 参数: `-Dserver.port=8000`, 否则端口冲突
@EnableAutoConfiguration
@SpringBootApplication(scanBasePackages = ["me.test"])
@Component
class SpringAmqpSubTest implements ApplicationContextAware {

    @Autowired
    ConnectionFactory connectionFactory

    @Autowired
    MySpringAmqpListener listener

    @Autowired
    AmqpAdmin amqpAdmin

    ConfigurableApplicationContext applicationContext
    SimpleMessageListenerContainer msgListenerContainer


    @Override
    void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext
    }

    void sub() {
        Exchange exchange = new TopicExchange(AMQP_EXCHANGE_NAME)
        Queue listeningQueue = amqpAdmin.declareQueue()
        String routingKey = MQTT_TOPIC.replace('/', '.')

        Binding queueBinding = BindingBuilder.bind(listeningQueue).to(exchange).with(routingKey);
        amqpAdmin.declareBinding(queueBinding)

        msgListenerContainer = new SimpleMessageListenerContainer();
        msgListenerContainer.setDefaultRequeueRejected(false);
        msgListenerContainer.setConnectionFactory(connectionFactory)
        msgListenerContainer.setMessageListener(listener)
        msgListenerContainer.setQueues(listeningQueue)
        //msgListenerContainer.setAutoStartup(false)
        // 内部默认是使用：SimpleAsyncTaskExecutor， 即一个任务启动一个线程，该线程只能自己手动去停止。
        // 生产环境应当使用 ThreadPoolTaskExecutor， WorkManagerTaskExecutor
        //msgListenerContainer.setTaskExecutor(...)
        msgListenerContainer.start()
    }

    @EventListener([MySpringAmqpListener.SubFinishedEvent])
    void onSubFinished(Object event) {
        println "------------- SpringAmqpSubTest : onSubFinished : $event"

        msgListenerContainer?.stop()
        msgListenerContainer.destroy()

        applicationContext.stop()
        applicationContext.close()

    }


    static void main(String[] args) {

        ConfigurableApplicationContext appContext = SpringApplication.run(SpringAmqpPubTest.class, args);

        SpringAmqpSubTest test = appContext.getBean(SpringAmqpSubTest)
        test.sub()
    }

}