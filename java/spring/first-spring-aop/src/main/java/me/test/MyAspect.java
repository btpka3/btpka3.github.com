
package me.test;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Aspect
@Service
public class MyAspect {

    private final Logger logger = LoggerFactory.getLogger(MyAspect.class);

    @Before("execution(* me.test.MyImpl.*(..))")
    public void doAccessCheck(JoinPoint jp) {

        logger.debug(jp.getSignature() + " is invoked.");
    }
}
