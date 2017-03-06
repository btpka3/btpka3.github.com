package me.test.first.spring.boot.cxf.conf

import me.test.first.spring.boot.cxf.controller.MyWsHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.config.annotation.*
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean

@Configuration
@EnableWebSocket
@EnableWebSocketMessageBroker
class WebSocketConf extends AbstractWebSocketMessageBrokerConfigurer
        implements WebSocketConfigurer {

    @Autowired
    MyWsHandler myWsHandler


    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(myWsHandler, "/myWs")
                .withSockJS()
        //.setClientLibraryUrl("http://localhost:8080/myapp/js/sockjs-client.js");
        //.setAllowedOrigins()
    }

    // --------------

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/hello")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.setApplicationDestinationPrefixes("/app");
        config.enableSimpleBroker("/topic", "/queue");
    }


    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxTextMessageBufferSize(8192);
        container.setMaxBinaryMessageBufferSize(8192);
        return container;
    }


}
