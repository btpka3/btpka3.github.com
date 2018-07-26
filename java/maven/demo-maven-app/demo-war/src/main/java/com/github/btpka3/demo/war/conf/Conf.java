package com.github.btpka3.demo.war.conf;

import com.github.btpka3.demo.lib.Aaa;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Conf {

    public Aaa aaa() {
        return new Aaa();
    }
}
