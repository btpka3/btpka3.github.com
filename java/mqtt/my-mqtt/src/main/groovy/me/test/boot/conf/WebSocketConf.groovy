package me.test.boot.conf

import me.test.boot.handler.MyMsgHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.config.annotation.*
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean
import org.springframework.web.socket.server.standard.TomcatRequestUpgradeStrategy
import org.springframework.web.socket.server.support.DefaultHandshakeHandler
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor

/**
 *
 */
@Configuration
@EnableWebSocket
class WebSocketConf {

    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxTextMessageBufferSize(8192);
        container.setMaxBinaryMessageBufferSize(8192);
        return container;
    }

    @Bean
    public DefaultHandshakeHandler handshakeHandler() {

        return new DefaultHandshakeHandler(
                new TomcatRequestUpgradeStrategy());
    }

    @Bean
    WebSocketMessageBrokerConfigurer WebSocketMessageBrokerConfigurer() {
        return new AbstractWebSocketMessageBrokerConfigurer() {

            @Override
           public void registerStompEndpoints(StompEndpointRegistry registry) {
                registry.addEndpoint("/portfolio")
                        .withSockJS()
                //.setClientLibraryUrl("http://localhost:8080/myapp/js/sockjs-client.js");
            }
        }
    }

    @Bean
    WebSocketConfigurer webSocketConfigurer(
            MyMsgHandler myMsgHandler,
            DefaultHandshakeHandler handshakeHandler
    ) {
        return new WebSocketConfigurer() {

            @Override
            void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
                registry
                        .addHandler(myMsgHandler, "/myMsgHandler")
                        .setHandshakeHandler(handshakeHandler)
                //.setAllowedOrigins("*")
                        .addInterceptors(new HttpSessionHandshakeInterceptor())
            }
        }
    }
}
