package me.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainStartDB {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext appCtxt = new ClassPathXmlApplicationContext(
                "applicationContext-startDB.xml");
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
