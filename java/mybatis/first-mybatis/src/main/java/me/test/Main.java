package me.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    private static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        ClassPathXmlApplicationContext appCtxt = new ClassPathXmlApplicationContext(
                "applicationContext.xml");
        while(true){
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }

}
