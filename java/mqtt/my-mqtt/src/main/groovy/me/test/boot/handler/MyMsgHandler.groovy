package me.test.boot.handler

import org.springframework.stereotype.Component
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler

/**
 *
 */
@Component
class MyMsgHandler extends TextWebSocketHandler {

    @Override
    void handleTextMessage(WebSocketSession session, TextMessage message) {
        // ...
    }
}
