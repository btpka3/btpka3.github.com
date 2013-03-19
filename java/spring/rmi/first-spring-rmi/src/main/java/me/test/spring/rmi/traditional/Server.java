package me.test.spring.rmi.traditional;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Server {

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
                "classpath:/traditional/server-context.xml");
        ctx.start();
    }
}
