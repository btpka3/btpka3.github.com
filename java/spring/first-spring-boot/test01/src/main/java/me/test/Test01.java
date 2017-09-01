package me.test;

import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.context.properties.*;
import org.springframework.context.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@SpringBootApplication
@Component
public class Test01 {
    @Autowired
    TestConfig testConfig;

    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext run = new SpringApplication(Test01.class).run(args);
        Test01 test = run.getBean(Test01.class);
        test.run();
    }

    public void run() throws Exception {
        testConfig.getBar().entrySet().forEach(e -> {
            System.out.println(e.getKey() + " " + e.getValue());
        });
    }

    @Configuration
    @ConfigurationProperties(ignoreUnknownFields = false, prefix = "foo")
    static class TestConfig {
        private Map<SomeEnum, String> bar = new HashMap<>();

        public Map<SomeEnum, String> getBar() {
            return bar;
        }

        public void setBar(Map<SomeEnum, String> bar) {
            this.bar = bar;
        }
    }

    public static enum SomeEnum {
        A_VALUE, B_VALUE, C_VALUE, D_VALUE
    }
}