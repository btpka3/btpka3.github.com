
package me.test;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Aspect
@Service
public class MyAspect {

    private final Logger logger = LoggerFactory.getLogger(MyAspect.class);

    @Autowired(required = false)
    @Qualifier("str")
    private String str = "000";

    @Before("within(*) && execution(* me.test.MyImpl.*(..))")
    public void doAccessCheck(JoinPoint jp) {

        logger.debug(jp.getSignature() + " is invoked. " + str);
    }
}
