package me.test.boot.controller

import com.fasterxml.jackson.databind.ObjectMapper
import groovy.util.logging.Slf4j
import org.springframework.amqp.AmqpException
import org.springframework.amqp.core.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.messaging.handler.annotation.Payload
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

    @Autowired
    Environment environment;

    @Autowired
    ObjectMapper objectMapper

    /*
======================== Properties
priority:	0
delivery_mode:	2
headers:
    __TypeId__:	me.test.boot.controller.MyMsg
content_encoding:	UTF-8
content_type:	application/jso

======================== Payload
{"name":"zhang3","birthday":1501651885216,"hobbies":["h1","h2"],"age":10,"memo":"MyTestController msg[0] : Wed Aug 02 13:31:25 CST 2017"}

     */

    @RequestMapping("/pub")
    @ResponseBody
    String pub() {

        3.times {

            MyMsg myMsg = new MyMsg()
            myMsg.name = "zhang3"
            myMsg.age = 10
            myMsg.birthday = new Date()
            myMsg.hobbies = ["h1", "h2"]
            myMsg.memo = "MyTestController msg[$it] : " + new Date()

            String exchange = environment.getProperty("a.prefix", "") +
                    AMQP_EXCHANGE_NAME;
            println "exchange = " + exchange
            amqpTemplate.convertAndSend(
                    exchange,
                    routingKey,
                    myMsg
//                    new MessagePostProcessor() {
//
//                        @Override
//                        Message postProcessMessage(Message message) throws AmqpException {
//
//                            message.getMessageProperties().getHeaders().remove("__TypeId__")
//                            return message
//                        }
//                    }
            );
        }

        return "OK : " + new Date()
    }


    @RequestMapping("/test01")
    @ResponseBody
    String test01() {
        String s = "{\"name\":\"zhang3\",\"birthday\":1501653000825,\"hobbies\":[\"h1\",\"h2\"],\"age\":10,\"memo\":\"MyTestController msg[2] : Wed Aug 02 13:50:00 CST 2017\"}";

        MyMsg myMsg = objectMapper.readValue(s, MyMsg.class)
        println "myMsg = " + myMsg
        return myMsg
    }

//    @MyAmqpMsgListener
//    void sub1(String msg) {
//        log.debug("sub1 : " + msg)
//    }

    @MyAmqpMsgListener
    void sub2(Message msg) {
        log.debug("sub2 : " + msg)
    }

    @MyAmqpMsgListener
    void sub3(
            @Payload
                    MyMsg myMsg,
//            // 不能同时使用不同的类型。
//            // 发送消息时，使用的类型A，如果接受消息时想使用类型B，则需要为 Jackson2JsonMessageConverter 配置 ClassMapper
//            @Payload
//                    Map<String, String> map,
            org.springframework.messaging.Message<MyMsg> msg,
            Message amqpMsg
    ) {
        log.debug("sub3 : "
                + "\n\t myMsg   : " + myMsg
//                + "\n\t map     : " + map
                + "\n\t msg     : " + msg
                + "\n\t amqpMsg : " + amqpMsg
                + "\n\t"
        )
    }

}
