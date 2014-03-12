
package me.test.anno;

import org.springframework.aop.PointcutAdvisor;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AnnoMain {

    /**
     * 使用 @Aspectj 注解示例。
     */
    public static void main(String[] args) {

        ClassPathXmlApplicationContext appCtx = new ClassPathXmlApplicationContext("application-context-anno.xml");
        appCtx.registerShutdownHook();

        int count = appCtx.getBeanDefinitionCount();
        System.out.printf("spring managed %d beans:%n", count);
        String[] beanNames = appCtx.getBeanDefinitionNames();
        for (String beanName : beanNames) {
            Object bean = appCtx.getBean(beanName);
            System.out.printf("\tbean name  : %s%n", beanName);
            System.out.printf("\tgetClass() : %s%n", bean.getClass());
            System.out.printf("\ttoString() : %s%n", bean);
            if (bean instanceof PointcutAdvisor) {
                PointcutAdvisor advisor = (PointcutAdvisor) bean;
                System.out.printf("\tpointcut   : %s%n", advisor.getPointcut());
                System.out.printf("\tadvice     : %s%n", advisor.getAdvice());
            }
            System.out.println();
        }

        System.out.println("-------------");

        Runnable taskA = (Runnable) appCtx.getBean("taskA");
        taskA.run();

        System.out.println("-------------");

        Runnable taskB = (Runnable) appCtx.getBean("taskB");
        taskB.run();

        System.out.println("-------------");

        appCtx.close();
    }

    /*
     * output:
     * -------------
     * taskA is running.
     * -------------
     * 111111111111 :{a=a1, b=b1}
     * taskB is running.
     * 222222222222 :{a=a1, b=b1}
     * -------------
     */
}
