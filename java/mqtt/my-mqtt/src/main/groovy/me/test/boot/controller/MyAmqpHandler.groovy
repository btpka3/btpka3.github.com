package me.test.boot.controller

import org.springframework.messaging.Message

interface MyAmqpHandler {


    @MyAmqpMsgListener
    void handle(Message msg)

}