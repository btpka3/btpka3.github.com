package me.test.first.spring.boot.test.context;

import me.test.first.spring.boot.test.context.a.CccSerivce;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author dangqian.zll
 * @date 2019-06-28
 */
public class ContextTest {
    @Test
    public void test() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("a.xml");
        CccSerivce cccSerivce = applicationContext.getBean(CccSerivce.class);
        System.out.println("" + cccSerivce);
    }
}
