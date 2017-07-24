package me.test.boot.controller

import groovy.util.logging.Slf4j
import org.springframework.amqp.core.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@Slf4j
class MyAmqpController {

    @Autowired
    AmqpAdmin amqpAdmin

    @Autowired
    AmqpTemplate amqpTemplate

    public static final String AMQP_EXCHANGE_NAME = "z.topic"//"amq.topic"
    public static final String routingKey = "z.ll"

    @RequestMapping("/pub")
    @ResponseBody
    String pub() {

        3.times {
            String content = "MyTestController msg[$it] : " + new Date()
            amqpTemplate.convertAndSend(AMQP_EXCHANGE_NAME, routingKey, content);
        }

        return "OK : " + new Date()
    }

    @MyAmqpMsgListener
    void sub1(String msg) {
        log.debug("sub1 : " + msg)
    }

    @MyAmqpMsgListener
    void sub2(Message msg) {
        log.debug("sub2 : " + msg)
    }

}
