package io.github.btpka3.netty.socks5.conf;

import org.springframework.boot.context.embedded.*;
import org.springframework.context.annotation.*;

@Configuration
public class TomcatConf {

    @Bean
    EmbeddedServletContainerCustomizer embeddedServletContainerCustomizer() {
        return new EmbeddedServletContainerCustomizer() {
            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {
                MimeMappings mappings = new MimeMappings(MimeMappings.DEFAULT);
                mappings.add("woff", "application/font-woff");
                mappings.add("woff2", "application/font-woff2");
                mappings.add("pac", "application/x-ns-proxy-autoconfig");
                container.setMimeMappings(mappings);
            }
        };
    }
}
