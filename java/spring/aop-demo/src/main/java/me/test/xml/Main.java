
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

/* output:
-------------
taskA is running.
-------------
111111111111 :{a=a1, b=b1}
taskB is running.
222222222222 :{a=a1, b=b1}
-------------
 */
}
