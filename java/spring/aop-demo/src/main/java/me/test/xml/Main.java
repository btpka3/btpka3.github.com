
package me.test.xml;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    /**
     * Spring声明式AOP示例。
     *
     */
    public static void main(String[] args) {

        ClassPathXmlApplicationContext appCtx = new ClassPathXmlApplicationContext("application-context-declare-aop.xml");
        appCtx.registerShutdownHook();

        System.out.println("-------------");

        Runnable taskA = (Runnable) appCtx.getBean("taskA");
        taskA.run();

        System.out.println("-------------");

        Runnable taskB = (Runnable) appCtx.getBean("taskB");
        taskB.run();

        System.out.println("-------------");

        appCtx.close();
    }

}
