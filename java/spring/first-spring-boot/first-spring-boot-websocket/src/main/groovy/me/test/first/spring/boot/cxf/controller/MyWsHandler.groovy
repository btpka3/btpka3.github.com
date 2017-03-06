package me.test.first.spring.boot.cxf.controller

import ch.qos.logback.classic.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler

/**
 *
 */
@Component
class MyWsHandler extends TextWebSocketHandler {

    static final Logger log = LoggerFactory.getLogger(MyWsHandler)


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        log.info("connected; " + session)
        TextMessage message = new TextMessage("Welcome, this is my first websocket test @ " + new Date());
        session.sendMessage(message);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {

        String msg = message.getPayload()
        log.debug("Msg received : " + msg)

        session.sendMessage(new TextMessage("RE : " + msg))
    }
}
