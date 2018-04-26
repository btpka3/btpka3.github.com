package me.test.first.spring.boot.integration.controller

import org.springframework.amqp.core.AmqpTemplate
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.integration.support.locks.LockRegistry
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.Lock

@RequestMapping("/test")
@Controller
class MyTestController {

    @Autowired
    RabbitMessagingTemplate msgTemplate

    @Autowired
    AmqpTemplate AmqpTemplate;

    @RequestMapping("")
    @ResponseBody
    Object test() {
        return "test " + new Date();
    }

    @RequestMapping("/send")
    @ResponseBody
    Object send(
            @RequestParam
                    String msg
    ) {

        msgTemplate.convertAndSend([
                msg : msg,
                date: new Date()
        ])
        return "sendOk : " + new Date()
    }

    @Autowired
    private LockRegistry lockRegistry;


    @RequestMapping("/lock")
    @ResponseBody
    Object lock() {

        String lockKey = "test.lock.key"
        Lock lock = lockRegistry.obtain(lockKey)
        if (lock.tryLock(1, TimeUnit.SECONDS)) {
            try {
                Thread.sleep(10 * 1000)
                return ["lock success", new Date(), lock]
            } finally {
                lock.unlock();
            }
        }


        return ["lock failed", new Date()]
    }


}
