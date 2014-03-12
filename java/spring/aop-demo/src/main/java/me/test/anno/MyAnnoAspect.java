
package me.test.anno;

import java.util.Map;

import javax.annotation.Resource;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@SuppressWarnings("rawtypes")
@Component("myAnnoAspect")
@Aspect
public class MyAnnoAspect {

    @Resource(name = "configMap")
    private Map configMap;

    @Pointcut("execution(* me.test.task.TaskB.run())")
    public void myAnnoPointcut() {

    }

    //@Before("execution(* me.test.task.TaskB.run())")
    @Before("myAnnoPointcut()") // ref @Pointcut
    public void myAnnoBefore() {

        System.out.println("11111111 - anno :" + configMap);
    }

}
